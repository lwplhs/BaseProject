package com.lwp.website.utils;

import com.lwp.website.config.SysConfig;
import com.lwp.website.entity.Vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * 现在将用户权限交给shiro进行管理
 * @Auther: liweipeng
 * @Date: 2020/07/04/17:04
 * @Description:
 */
@Component
@Deprecated
public class UserRedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRedisUtil.class);
    private static SysConfig sysConfig;

    private static RedisUtil redisUtil;

    /**
     * 用户登录redis 中session的保存时间
     */
    private static int USERSESSIONTIMEECPIRE = 60 * 30;

    private static int ERRORCOUNTTIMEECOIRE = 60 * 10;

    @Autowired
    public void setSysConfig(SysConfig sysConfig){
        UserRedisUtil.sysConfig = sysConfig;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil){
        UserRedisUtil.redisUtil = redisUtil;
    }

    /**
     * 增加获取更新 session登录状态
     * @param request
     * @param userVo
     */
    public static void insertOrUpdateUserSession(HttpServletRequest request, UserVo userVo){
        String defaultCookie = sysConfig.getDefaultCookie();
        String loginUserKey = sysConfig.getLoginUser();
        Cookie cookie = TaleUtils.cookieRaw(defaultCookie, request);
        if(null != cookie) {
            String cookieValue = cookie.getValue();
            String key = loginUserKey + cookieValue;
            Object obj = redisUtil.get(key);
            if(StringUtil.isNotNull(obj)){
                redisUtil.expire(key, USERSESSIONTIMEECPIRE);
            }else {
                redisUtil.set(key, userVo, USERSESSIONTIMEECPIRE);
            }
        }
        insertOrUpdateSessionUser(request,userVo);
    }

    /**
     * 绑定用户id和浏览器标识 session
     * 使用redis的hash表
     */
    public static void insertOrUpdateSessionUser(HttpServletRequest request, UserVo userVo){
        String defaultCookie = sysConfig.getDefaultCookie();
        Cookie cookie = TaleUtils.cookieRaw(defaultCookie, request);
        if(StringUtil.isNotNull(cookie)) {
            String cookieValue = cookie.getValue();
            String session = String.valueOf(redisUtil.hget(sysConfig.getUserSession(),userVo.getId()));
            if(StringUtil.isNotNull(session) && !session.equals(cookieValue)) {
                //做下线处理
                String loginKey = sysConfig.getLoginUser();
                redisUtil.del(loginKey+session);
            }
            redisUtil.hset(sysConfig.getUserSession(), userVo.getId(), cookieValue, USERSESSIONTIMEECPIRE);
        }
    }

    /**
     * 增加错误次数
     * @param request
     * @param count
     */
    public static void insertErrorCount(HttpServletRequest request, int count){
        String defaultCookie = sysConfig.getDefaultCookie();
        String loginErrorCountKey = sysConfig.getLoginErrorCount();
        Cookie cookie = TaleUtils.cookieRaw(defaultCookie, request);
        if(null != cookie) {
            String cookieValue = cookie.getValue();
            String key = loginErrorCountKey + cookieValue;
            redisUtil.set(key, count, ERRORCOUNTTIMEECOIRE);
        }
    }

    /**
     * 获取错误次数
     * @param request
     * @return
     */
    public static Integer getErrorCount(HttpServletRequest request){
        String defaultCookie = sysConfig.getDefaultCookie();
        String loginErrorCountKey = sysConfig.getLoginErrorCount();
        Cookie cookie = TaleUtils.cookieRaw(defaultCookie, request);
        if(StringUtil.isNotNull(cookie)) {
            String cookieValue = cookie.getValue();
            String key = loginErrorCountKey + cookieValue;
            Integer error_count = StringUtil.isNull(redisUtil.get(key)) ? 0 : Integer.parseInt(redisUtil.get(key).toString());
            return error_count;
        }
        return 0;
    }

    /**
     * 删除用户session 清除登录态
     * @param request
     */
    public static void delUserSession(HttpServletRequest request){
        String loginUserKey = sysConfig.getLoginUser();
        String defaultCookie = sysConfig.getDefaultCookie();
        Cookie cookie = TaleUtils.cookieRaw(defaultCookie, request);
        if(StringUtil.isNotNull(cookie)) {
            String cookieValue = cookie.getValue();
            String key = loginUserKey + cookieValue;
            redisUtil.del(key);
        }
    }


}
