package com.lwp.website.controller.admin.system;

import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Bo.SystemBo;
import com.lwp.website.entity.Bo.SystemConfigBo;
import com.lwp.website.entity.Vo.SystemVo;
import com.lwp.website.res.ResourceManage;
import com.lwp.website.service.SystemService;
import com.lwp.website.service.WebUploadService;
import com.lwp.website.utils.UploadUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/13/17:22
 * @Description:
 */
@Controller
@RequestMapping("/admin/system")
public class SystemController extends BaseController {

    @Resource
    private SystemService systemService;

    @Resource
    private WebUploadService webUploadService;

    @GetMapping("/manager")
    public String toPageManager(Model model){
        SystemBo systemBo = systemService.getSystemConfig();
        model.addAttribute("systemBo",systemBo);
        String path = webUploadService.getPathById(systemBo.getLogo());
        model.addAttribute("logoPath",path);
        return this.render("/admin/system/manager");
    }

    /**
     *
     * @param request
     * @param response
     * @param systemBo
     * @return
     */
    @PostMapping("/saveSystem")
    @ResponseBody
    public RestResponseBo saveSystem(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @ModelAttribute SystemBo systemBo){
        Map map = systemService.saveSystem(systemBo);
        String logo = systemBo.getLogo();
        String copyright = systemBo.getCopyright();
        String logoPath = webUploadService.getPathById(logo);
        SystemConfigBo systemConfigBo = (SystemConfigBo) ResourceManage.getBean("systemConfigBo");
        systemConfigBo.setLogo(logoPath);
        systemConfigBo.setCopyright(copyright);
        return RestResponseBo.ok(map.get("errCode"),map.get("errMsg"));
    }

    @Bean("systemConfigBo")
    public SystemConfigBo setSystemBoBean(){
        SystemConfigBo systemConfigBo = new SystemConfigBo();
        SystemBo systemBo = systemService.getSystemConfig();
        String logo = systemBo.getLogo();
        String logoPath = webUploadService.getPathById(logo);
        systemConfigBo.setLogo(logoPath);
        String copyright = systemBo.getCopyright();
        systemConfigBo.setCopyright(copyright);
        return systemConfigBo;
    }


}
