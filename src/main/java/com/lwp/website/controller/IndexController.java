package com.lwp.website.controller;

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
        return this.render("/index");
    }

    @GetMapping(value={"/getPage/{name}", "/getPage/{name}.html"})
    public String getHtml(HttpServletResponse response, @PathVariable String name){

        return this.render(name);
    }
    public static void main(String[] args){
        IntStream.of().parallel().min().getAsInt();
    }
}
