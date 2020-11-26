package com.lwp.website.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.business.RegistrationService;
import com.lwp.website.utils.TaleUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Resource
    private RegistrationService registrationService;

    @GetMapping(value = "/manager")
    public String manager(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        JSONObject jsonObject = new JSONObject();
        UserVo userVo = TaleUtils.getLoginUser(request);
        model.addAttribute("user",userVo);
        String userId = userVo.getId();
        //绑定主表数据
        JSONObject reg = registrationService.getRegistrationById(userId);

        model.addAttribute("reg",reg);
        String masterId = reg.getString("id");
        //项目基本信息子表
        List<JSONObject> baseList = registrationService.getProjectBaseInfoByMasterId(masterId);
        model.addAttribute("baseList",baseList);
        //获奖信息子表
        List<JSONObject> awardList = registrationService.getAwardCertificateByMasterId(masterId);
        model.addAttribute("awardList",awardList);

        //介绍情况
        List<JSONObject> intrList = registrationService.getIntroductionByMasterId(masterId);
        model.addAttribute("intrList",intrList);


        return this.render("/business/registration/manager");
    }

    @GetMapping("/upload")
    public String uploadPage(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model){
        return "/business/registration/upload";
    }

    @GetMapping("/report1")
    public String report1(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        return this.render("/business/registration/report1");
    }
    @GetMapping("/report2")
    public String report2(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        return this.render("/business/registration/report2");
    }

    @GetMapping("/report3")
    public String report3(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        return this.render("/business/registration/report3");
    }

    @PostMapping("/save")
    @ResponseBody
    public RestResponseBo saveData(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "jsondata") String jsondata,
                                   @RequestParam(value = "baseList") String baseList,
                                   @RequestParam(value = "certList") String certList,
                                   @RequestParam(value = "intrList") String intrList){


        //主表数据
        UserVo userVo = TaleUtils.getLoginUser(request);
        String masterId = registrationService.saveRegistration(jsondata,userVo);
        registrationService.saveProjectBaseInfo(baseList,masterId);
        registrationService.saveAwardCertificate(certList,masterId);
        registrationService.savIntroduction(intrList,masterId);

        return RestResponseBo.ok(1,"保存成功");
    }


    @PostMapping("/getReport1")
    public String getReport1(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model,
                                     @RequestParam(value = "searchKey") String searchKey){

        List<JSONObject> list = registrationService.getListReport1(searchKey);
        model.addAttribute("info",list);
        if(null != list && list.size() >0){
            model.addAttribute("total",list.size());
        }else {
            model.addAttribute("total","0");
        }
        return this.render("/business/registration/report1::list");
    }

    @PostMapping("/getReport2")
    public String getReport2(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model,
                             @RequestParam(value = "searchKey") String searchKey){
        List<JSONObject> list = registrationService.getListReport2(searchKey);
        model.addAttribute("info",list);
        if(null != list && list.size() >0){
            model.addAttribute("total",list.size());
        }else {
            model.addAttribute("total","0");
        }
        return this.render("/business/registration/report2::list");
    }
    @PostMapping("/getReport3")
    public String getReport3(HttpServletRequest request,
                             HttpServletResponse response,
                             Model model,
                             @RequestParam(value = "searchKey") String searchKey){

        List<JSONObject> list = registrationService.getListReport3(searchKey);
        model.addAttribute("info",list);
        if(null != list && list.size() >0){
            model.addAttribute("total",list.size());
        }else {
            model.addAttribute("total","0");
        }
        return this.render("/business/registration/report3::list");
    }
}
