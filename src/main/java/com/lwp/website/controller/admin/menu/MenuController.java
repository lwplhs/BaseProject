package com.lwp.website.controller.admin.menu;

import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.MenuVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.MenuService;
import com.lwp.website.utils.InvalidUtil;
import com.lwp.website.utils.TaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/15/14:46
 * @Description:
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

    private Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private MenuService menuService;

    /**
     * 菜单管理
     * 跳转到 manager页面
     * @return
     */
    @GetMapping("/manager")
    public String manager(){
        return this.render("/admin/menu/manager");
    }

    /**
     * 菜单管理
     * 跳转到 子表详情页面
     * @return
     */
    @GetMapping("/subDetail")
    public String subDetail(){

        return this.render("/admin/menu/subDetail");
    }

    /**
     * 菜单管理
     * 根据id id = 0 是首级节点
     * 跳转到新增页面
     * @param id
     * @return
     */
    @GetMapping("/add")
    public String addPage(@RequestParam(value = "id",defaultValue = "0") String id,
                          Model model){
        String series = "1";
        String pid = id;
        int status = 0;
        String icon = "";
        String url = "";
        String pname = menuService.getMenuById(id).getName();
        if("0".equals(id)){
            series = "1";
        }else {
            series = "2";
        }
        model.addAttribute("series",series);
        model.addAttribute("pid",pid);
        model.addAttribute("status",status);
        model.addAttribute("icon",icon);
        model.addAttribute("url",url);
        model.addAttribute("pname",pname);
        model.addAttribute("action","new");
        return this.render("/admin/menu/edit");
    }

    /**
     * 菜单管理 跳转到edit页面 绑定数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id",defaultValue = "") String id,
                           Model model){

        String series = "1";
        String pid = "";
        int status = 0;
        String name = "";
        String icon = "";
        String url = "";

        MenuVo menuVo = menuService.getMenuById(id);
        if(null != menuVo){
            series = menuVo.getSeries();
            pid = menuVo.getPid();
            status = Integer.parseInt(menuVo.getStatus());
            name = menuVo.getName();
            icon = menuVo.getIcon();
            url = menuVo.getUrl();

        }
        String pname = menuService.getMenuById(pid).getName();
        model.addAttribute("series",series);
        model.addAttribute("pid",pid);
        model.addAttribute("pname",pname);
        model.addAttribute("status",status);
        model.addAttribute("name",name);
        model.addAttribute("id",id);
        model.addAttribute("icon",icon);
        model.addAttribute("url",url);

        model.addAttribute("action","edit");
        return this.render("/admin/menu/edit");

    }

    /**
     * 跳转到 view 页面 绑定数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/view")
    public String viewPage(@RequestParam(value = "id",defaultValue = "") String id,
                           Model model){
        String series = "1";
        String pid = "";
        int status = 0;
        String name = "";
        String icon = "";
        String url = "";

        MenuVo menuVo = menuService.getMenuById(id);
        if(null != menuVo){
            series = menuVo.getSeries();
            pid = menuVo.getPid();
            status = Integer.parseInt(menuVo.getStatus());
            name = menuVo.getName();
            icon = menuVo.getIcon();
            url = menuVo.getUrl();

        }
        String pname = menuService.getMenuById(pid).getName();
        model.addAttribute("series",series);
        model.addAttribute("pid",pid);
        model.addAttribute("pname",pname);
        model.addAttribute("status",status);
        model.addAttribute("name",name);
        model.addAttribute("id",id);
        model.addAttribute("icon",icon);
        model.addAttribute("url",url);
        model.addAttribute("action","view");
        return this.render("/admin/menu/edit");
    }


    /**
     * 菜单管理manager 获取菜单树状图数据
     * @return
     */
    @PostMapping(value = "/getData")
    @ResponseBody
    public RestResponseBo getData(){
        LOGGER.info("-----------------------------开始获取功能菜单");
        List list = menuService.getMenuList();
        RestResponseBo<List> dictVoRestResponseBo = new RestResponseBo<List>(true,list);
        LOGGER.info("-----------------------------获取功能菜单结束");
        return dictVoRestResponseBo;
    }

    /**
     * 获取manager 右侧子菜单数据
     * @param id 当前点击菜单的id
     * @return
     */
    @PostMapping(value = "/getSubData")
    @ResponseBody
    public RestResponseBo getSubData(String id){
        LOGGER.info("-----------------------------获取功能菜单");
        List list = menuService.getSubData(id);
        RestResponseBo<List> restResponseBo = new RestResponseBo<List>(true,list);
        return restResponseBo;
    }

    /**
     * 保存菜单信息
     * @param request
     * @param response
     * @param menuVo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/saveMenu")
    @ResponseBody
    public RestResponseBo saveDict(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @Validated @ModelAttribute MenuVo menuVo,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存功能菜单------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = menuService.saveMenu(menuVo,userVo);
        LOGGER.info("-------------------保存功能菜单结束------------------");
        return RestResponseBo.ok(result);
    }

    /**
     * 根据 菜单id 获取菜单级别
     * @param id
     * @return
     */
    @PostMapping(value = "/getSeriesById")
    @ResponseBody
    public RestResponseBo getSeriesById(String id){
        LOGGER.info("-----------------开始获取功能菜单级别，id:"+id);
        String series = menuService.getSeriesById(id);
        LOGGER.info("-----------------获取功能菜单级别成功-----------------");
        return RestResponseBo.ok(series,1,"获取功能菜单级别成功");
    }

    /**
     * 根据id更新 状态 1 启用 ，停用 2 删除
     * @param type
     * @param id
     * @return
     */
    @PostMapping(value = "updateMenuStatusById")
    @ResponseBody
    public RestResponseBo updateDictStatusById(@RequestParam(value = "type") String type,
                                               @RequestParam(value = "id") String id,
                                               HttpServletRequest request){

        LOGGER.info("-------------------修改功能菜单状态开始------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = menuService.updateMenuWithType(type,id,userVo);
        LOGGER.info("-------------------修改功能菜单状态结束------------------");
        if(bool){
            return RestResponseBo.ok(1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败，请刷新数据!");
        }
    }

    /**
     * 功能菜单拖拽事件
     * @param dragId
     * @param dropId
     * @param request
     * @return
     */
    @PostMapping(value = "drag")
    @ResponseBody
    public RestResponseBo drag(@RequestParam(value = "dragId") String dragId,
                               @RequestParam(value = "dropId") String dropId,
                               HttpServletRequest request){
        LOGGER.info("-------------------开始进行拖拽-----------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = menuService.drag(dragId,dropId,userVo);
        LOGGER.info("--------------------拖拽结束--------------------------");
        if(bool){
            return RestResponseBo.ok(1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败");
        }
    }
}
