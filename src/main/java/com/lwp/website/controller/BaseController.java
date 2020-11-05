package com.lwp.website.controller;

import com.lwp.website.utils.MapCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {
    public static String THEME = "";

    private HttpServletRequest request;
    private HttpServletResponse response;

    protected MapCache cache = MapCache.single();


    /**
     * 跳转到页面主题
     * @param viewName
     * @return
     */
    public String render(String viewName){
        if(viewName.startsWith("/")) {
            return THEME + viewName;
        }else {
            return THEME+ "/" + viewName;
        }
    }

    public BaseController title(HttpServletRequest request, String title){
        request.setAttribute("title",title);
        return this;
    }

    public BaseController keywords(HttpServletRequest request, String keywords){
        request.setAttribute("keywords",keywords);
        return this;
    }

    public String render_404(){
        return "comm/error_404";
    }
}
