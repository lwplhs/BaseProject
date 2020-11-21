package com.lwp.website.controller.business;

import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Vo.UserVo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/20/10:41
 * @Description:
 */
@Controller
@RequestMapping("/admin/registration")
public class RegistrationController extends BaseController {


    private static Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @GetMapping(value = "/manager")
    public String manager(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        UserVo userVo = new UserVo();
        model.addAttribute("user",userVo);
        return this.render("/business/registration/manager");
    }

}
