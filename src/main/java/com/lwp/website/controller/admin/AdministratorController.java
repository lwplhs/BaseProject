package com.lwp.website.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.UserService;
import com.lwp.website.utils.InvalidUtil;
import com.lwp.website.utils.TaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
 * 管理员用户Controller
 * @Auther: liweipeng
 * @Date: 2020/11/23/15:36
 * @Description:
 */
@Controller
@RequestMapping("/admin/administrator")
public class AdministratorController extends BaseController {


    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorController.class);
    @Resource
    private UserService userService;

    /**跳转到管理员管理列表 -不查询数据 不查询总数 为0的话不显示分页 显示无数据图片
     *
     * @return
     */
    @GetMapping(value = "/manager")
    public String toPageManager(){
        return this.render("/admin/administrator/manager");
    }

    @GetMapping(value = "/add")
    public String toPageAdd(Model model){
        //管理员
        UserVo userVo = new UserVo();
        userVo.setType("1");
        model.addAttribute("user",userVo);
        model.addAttribute("action","add");
        return this.render("/admin/administrator/edit");
    }

    @GetMapping(value = "/edit")
    public String toPageEdit(Model model,
                             @RequestParam(value = "id") String id){
        UserVo userVo = userService.queryUserById(id);
        model.addAttribute("user",userVo);
        //获取权限分组数据 将角色id转换为角色名称
        String roleName = userService.getRoleName(userVo);
        model.addAttribute("group",roleName);
        model.addAttribute("action","edit");
        return this.render("/admin/administrator/edit");
    }




    /**
     * 根据分页 获取数据
     * 增加关键字查询
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @PostMapping(value = "/getAdministrator")
    public String getUser(Model model,
                          @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                          @RequestParam(value = "limit",defaultValue = "10") int limit,
                          @RequestParam(value = "searchKey", defaultValue = "") String searchKey){
        LOGGER.info("-------------------获取管理员用户数据 第"+pageNum+"页，"+limit+"条数据------------------");
        Page<UserVo> page = PageHelper.startPage(pageNum,limit);
        List<UserVo> list = userService.getAdminUserList(searchKey);
        LOGGER.info("-------------------获取管理员用户数据结束------------------");
        model.addAttribute("userList",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/administrator/manager::list");
    }

    /**
     * 根据id更新数据
     * @param ids
     * @param type 1 启用 2 删除
     * @return
     */
    @PostMapping(value = "/updateAdministratorStatus")
    @ResponseBody
    public RestResponseBo updateUser(@RequestParam(value = "ids") String ids,
                                     @RequestParam(value = "type") String type,
                                     @RequestParam(value = "searchKey",defaultValue = "") String searchKey,
                                     HttpServletRequest request){
        LOGGER.info("-------------------修改管理员状态------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = userService.updateUser(ids,type,userVo);
        if(bool){
            String totalCount = String.valueOf(userService.getAdminUserList(searchKey).size());
            return RestResponseBo.ok(totalCount,1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败，请刷新数据");
        }
    }


    /**
     *
     * @param request
     * @param response
     * @param userVo
     * @param bindingResult
     * @return
     */
    @PostMapping("/saveAdministrator")
    @ResponseBody
    @Transactional
    public RestResponseBo saveUser(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @Validated @ModelAttribute UserVo userVo,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存管理员------------------");
        UserVo userLoginVo = TaleUtils.getLoginUser(request);
        String result = userService.saveUser(userVo,userLoginVo);
        LOGGER.info("-------------------保存管理员结束------------------");
        return RestResponseBo.ok(result);
    }
    @PostMapping("/updateDefaultPwd")
    @ResponseBody
    @Transactional
    public RestResponseBo updateDefaultPwd(HttpServletRequest request,
                                           HttpServletResponse response,
                                           @RequestParam(value = "ids") String ids){
        Boolean b = userService.defaultPwd(ids);
        if (b){
            return RestResponseBo.ok(1,"初始化成功");
        }
        return RestResponseBo.fail(0,"初始化失败，请重试！");

    }

}
