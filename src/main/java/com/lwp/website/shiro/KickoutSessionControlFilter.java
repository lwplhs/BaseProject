package com.lwp.website.shiro;

import com.alibaba.fastjson.JSON;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.utils.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/15/17:10
 * @Description: shiro 自定义filter 实现 并发登录控制
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
    /** 踢出后到的地址 */
    private String kickoutUrl;

    /**  踢出之前登录的/之后登录的用户 默认踢出之前登录的用户 */
    private boolean kickoutAfter = false;

    /**  同一个帐号最大会话数 默认1 */
    private int maxSession = 1;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private RedisUtil redisUtil;

    public static final String DEFAULT_KICKOUT_CACHE_KEY_PREFIX = "shiro:cache:kickout:";
    private String keyPrefix = DEFAULT_KICKOUT_CACHE_KEY_PREFIX;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    private String getRedisKickoutKey(String username) {
        return this.keyPrefix + username;
    }

    /**
     * 是否允许访问，返回true表示允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }
    /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        Boolean b = subject.isAuthenticated();
        if(!subject.isAuthenticated()) {
            //如果没有登录，直接进行之后的流程
            return true;
        }

        //如果有登录,判断是否访问的为静态资源，如果是游客允许访问的静态资源,直接返回true
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String path = httpServletRequest.getServletPath();
        // 如果是静态文件，则返回true
        if (isStaticFile(path)){
            return true;
        }


        Session session = subject.getSession();
        //这里获取的User是实体 因为我在 自定义ShiroRealm中的doGetAuthenticationInfo方法中
        //new SimpleAuthenticationInfo(user, password, getName()); 传的是 User实体 所以这里拿到的也是实体,如果传的是userName 这里拿到的就是userName
        String username = ((UserVo) subject.getPrincipal()).getUsername();
        Serializable sessionId = session.getId();

        // 初始化用户的队列放到缓存里
        LinkedList<Serializable> deque = null;
        Object object = redisUtil.get(getRedisKickoutKey(username));
        if(object == null) {
            deque = new LinkedList<Serializable>();
        }else {
            deque = JSON.parseObject(JSON.toJSONString(object),LinkedList.class);
        }

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }

        //如果队列里的sessionId数超出最大会话数，开始踢人
        while(deque.size() > maxSession) {
            Serializable kickoutSessionId = null;
            if(kickoutAfter) { //如果踢出后者
                kickoutSessionId=deque.getFirst();
                kickoutSessionId = deque.removeFirst();
            } else { //否则踢出前者
                kickoutSessionId = deque.removeLast();
            }
            try {
                Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                if(kickoutSession != null) {
                    //设置会话的kickout属性表示踢出了
                    kickoutSession.setAttribute("kickout", true);
                }
            } catch (Exception e) {//ignore exception
                e.printStackTrace();
            }
        }

        redisUtil.set(getRedisKickoutKey(username), deque);

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) {
            }
            WebUtils.issueRedirect(request, response, kickoutUrl);
            return false;
        }
        return true;
    }

    private boolean isStaticFile(String path) {
        String staticUri = "";//resourceUrlProvider.getForLookupPath(path);
        return false;
    }
}
