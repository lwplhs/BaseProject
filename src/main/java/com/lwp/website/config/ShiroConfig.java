package com.lwp.website.config;

import com.lwp.website.entity.Bo.Sn;
import com.lwp.website.shiro.KickoutSessionControlFilter;
import com.lwp.website.shiro.LoginFilter;
import com.lwp.website.shiro.MyRedisManager;
import com.lwp.website.shiro.MyShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/14/21:34
 * @Description:
 */
@Configuration
public class ShiroConfig {

    @Resource
    private Sn sn;

    @Bean
    public Cookie cookieDAO() {
        Cookie cookie=new SimpleCookie();
        String defaultCookie = "MybaseprojectCookie";
        cookie.setName(defaultCookie);
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        cookie.setMaxAge(-1);
        return cookie;
    }

    /**
     * 设置自定义的验证方式
     * 将自己的验证方式加入容器
     * @return
     */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件 插件继承了shiro 中的接口
     * 使用自定义的MyRedisManager 重写RedisManager方法，
     *
     * @return
     */
    @Bean(name="redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new MyRedisManager();
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        // 配置缓存过期时间
//        redisManager.setExpire(expireTime);
//        redisManager.setTimeout(timeOut);
        // redisManager.setPassword(password);
        return redisManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     * 配置会话管理器，设定会话超时及保存
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //将修改的cookie放入sessionManager中
        sessionManager.setSessionIdCookie(cookieDAO());
        sessionManager.setSessionDAO(redisSessionDAO());
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 让某个实例的某个方法的返回值注入为Bean的实例
     * Spring静态注入
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(new Object[]{securityManager()});
        return factoryBean;
    }

    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 必须（thymeleaf页面使用shiro标签控制按钮是否显示）
     * 未引入thymeleaf包，Caused by: java.lang.ClassNotFoundException: org.thymeleaf.dialect.AbstractProcessorDialect
     * @return
     */
/*    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }*/


    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     *  // 权限管理，配置主要是Realm的管理认证
     * @return
     */
    @Bean(name = "securityManager")

    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    // 加入注解的使用，不加入这个注解不生效
    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier(value = "securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier(value = "securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        //访问的是后端url的地址，这里要写base 服务的公用登录接口。 使用toLogin进行拦截，跳转到/admin/login
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 登录成功后要跳转的链接；现在应该没用
        //shiroFilterFactoryBean.setSuccessUrl("/index");

        // 未授权界面;可以写个公用的403页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //自定义拦截器限制并发人数
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        filtersMap.put("kickout", kickoutSessionControlFilter());
        filtersMap.put("toLogin",loginFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);


        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/media/**", "anon");
        //对 /admin/login 不进行拦截
        filterChainDefinitionMap.put("/admin/login","anon");
        //对/toLogin进行拦截
        filterChainDefinitionMap.put("/toLogin","toLogin,authc");
        //logout是shiro提供的过滤器
        //filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/index","anon");
        //filterChainDefinitionMap.put("/**","anon");
        filterChainDefinitionMap.put("/admin/**","kickout,authc");
       // filterChainDefinitionMap.put("/**","anon");

        //filterChainDefinitionMap.put("/swagger-ui.html#", "anon");

        //filterChainDefinitionMap.put("/base/test", "authc");

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了,加上这个会导致302，请求重置，暂不明白原因
        //filterChainDefinitionMap.put("/logout", "logout");
        //配置某个url需要某个权限码
        //filterChainDefinitionMap.put("/hello", "perms[how_are_you]");

        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问;user:remember me的可以访问-->
//        filterChainDefinitionMap.put("/fine", "user");
        //filterChainDefinitionMap.put("/focus/**", "ad");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
    /**
     * 并发登录控制
     * @return
     */
    @Bean(name = "kickoutSessionControlFilter")
    public KickoutSessionControlFilter kickoutSessionControlFilter(){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //用于根据会话ID，获取会话进行踢出操作的；
        //自动注入
        // kickoutSessionControlFilter.setSessionManager(sessionManager());
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //自动注入
        //kickoutSessionControlFilter.setRedisManager(redisManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/toLogin");
        return kickoutSessionControlFilter;
    }

    @Bean(name = "loginFilter")
    public LoginFilter loginFilter(){
        LoginFilter loginFilter = new LoginFilter();
        return loginFilter;
    }

    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


}
