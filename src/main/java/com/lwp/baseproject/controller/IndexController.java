package com.lwp.baseproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.IntStream;

@Controller
public class IndexController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = {"/index","/"})
    public String index(){
        return this.render("index");
    }

    @RequestMapping(value = "/2020/happyBirthday")
    public String birthday(){
        return this.render("/birthday/2020/index");
    }
    @RequestMapping(value = "/2020/happyBirthday-add")
    public String birthday1(){
        return this.render("/birthday/2020/index1");
    }

    @GetMapping(value = "/chat")
    public String chat(){
        LOGGER.info("跳转到聊天室");
        return this.render("chat_room");
    }

    @GetMapping(value={"/getPage/{name}", "/getPage/{name}.html"})
    public String getHtml(HttpServletResponse response, @PathVariable String name){

        return this.render(name);
    }
    public static void main(String[] args){
        IntStream.of().parallel().min().getAsInt();
    }
}
