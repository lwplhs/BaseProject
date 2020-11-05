package com.lwp.website.exception;

import com.lwp.website.entity.Bo.RestResponseBo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/15/10:28
 * @Description:
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public RestResponseBo defaultExceptionHandler(HttpServletRequest req, Exception e){

        return RestResponseBo.fail(-1,"对不起，你没有访问权限！");
    }


    @ExceptionHandler(value = UnknownAccountException.class)
    @ResponseBody
    public RestResponseBo unknownAccountException(HttpServletRequest request, Exception e){
        return RestResponseBo.fail(-1,"账号或密码错误");
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public RestResponseBo throwAuthenticationException(HttpServletRequest req, Exception e){
        return RestResponseBo.fail(-1,"账号验证异常，请重新登录！");
    }
}
