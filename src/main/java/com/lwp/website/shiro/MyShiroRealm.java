package com.lwp.website.shiro;

import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/14/21:28
 * @Description:
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    /**
     * 1.授权方法，在请求需要操作码的接口时会执行此方法。不需要操作码的接口不会执行
     * 2.实际上是 先执行 AuthorizingRealm，自定义realm的父类中的 getAuthorizationInfo方法，
     * 逻辑是先判断缓存中是否有用户的授权信息（用户拥有的操作码），如果有 就直返回不调用自定义 realm的授权方法了，
     * 如果没缓存，再调用自定义realm，去数据库查询。
     * 用库查询一次过后，如果 在安全管理器中注入了 缓存，授权信息就会自动保存在缓存中，下一次调用需要操作码的接口时，
     * 就肯定不会再调用自定义realm授权方法了。   网上有分析AuthorizingRealm，shiro使用缓存的过程
     * 3.AuthorizingRealm 有多个实现类realm，推测可能是把 自定义realm注入了安全管理器，所以才调用自定义的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();

        UserVo userVo = (UserVo) principalCollection.getPrimaryPrincipal();

        //simpleAuthorInfo.addObjectPermission();

        return simpleAuthorInfo;
    }

    /**
     * 自定义 算法类型、期望次数。
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        //matcher.setHashIterations(1024);
        super.setCredentialsMatcher(matcher);
    }

    /**
     * 认证方法
     * 1.和授权方法一样，AuthenticatingRealm的getAuthenticationInfo，先判断缓存是否有认证信息，没有就调用
     * 但试验，登录之后，再次登录，发现还是调用了认证方法，说明第一次认证登录时，没有将认证信息存到缓存中。不像授权信息，
     * 将缓存注入安全管理器，就自动保存了授权信息。 难道无法 防止故意多次登录 ，按理说不应该啊？
     * 2  可以在登录controller简单用session是否有key 判断是否登录？
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从Controller里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String account = token.getUsername();
        UserVo userVo = userService.queryUserByUserName(account);
        if (userVo == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userVo,userVo.getPassword(),getName());
        return info;
    }

    @Override
    public String getName() {
        return "MyShiroRealm";
    }
}
