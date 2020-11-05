package com.lwp.website.controller.admin.UEditor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UEditorController {

    @RequestMapping(value = "/config")
    public void config(HttpServletResponse response, HttpServletRequest request){
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try{
            String exec = new ActionEnter(request,rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
