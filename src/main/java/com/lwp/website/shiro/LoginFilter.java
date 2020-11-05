package com.lwp.website.shiro;

import com.lwp.website.entity.Vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/26/17:57
 * @Description:
 */
public class LoginFilter extends AccessControlFilter {

    private void toLogin(PrintWriter out, String url){

        out.print("<script   language= 'javascript'>  ");
        out.print("top.location.href=\""+url+"\";");
        out.print("</script>");

    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {

        //判断是否登录，没有登录跳转到 登录界面 使用 out返回。可以进行全页面跳转，
        UserVo userVo = (UserVo) SecurityUtils.getSubject().getPrincipal();
        if(null == userVo){
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        String url = "/admin/login";
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        this.toLogin(out, url);
        return false;
    }
}
