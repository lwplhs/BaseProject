package com.lwp.website.controller.admin.menu;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.MenuVo;
import com.lwp.website.entity.Vo.RoleMenuVo;
import com.lwp.website.entity.Vo.RoleVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.MenuService;
import com.lwp.website.service.RoleService;
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
 *
 * @Auther: liweipeng
 * @Date: 2020/11/16/18:07
 * @Description:
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    /**跳转到管理列表 -不查询数据 不查询总数 为0的话不显示分页 显示无数据图片
     *
     * @return
     */
    @GetMapping(value = "/manager")
    public String toPageManager(){
        return this.render("/admin/role/manager");
    }

    /**
     *
     * 跳转到新增页面
     * @return
     */
    @GetMapping("/add")
    public String addPage(Model model){

        model.addAttribute("action","new");
        return this.render("/admin/role/edit");
    }
    /**
     * 跳转到编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id",defaultValue = "") String id,
                           Model model){

        String name = "";
        String desc = "";
        String status = "";

        RoleVo roleVo = roleService.getRoleById(id);
        if(null != roleVo){
            name = roleVo.getName();
            desc = roleVo.getDesc();
            status = roleVo.getStatus();
        }
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("desc",desc);
        model.addAttribute("status",status);

        model.addAttribute("action","edit");
        return this.render("/admin/role/edit");

    }

    /**
     * 根据分页 获取数据
     * 增加关键字查询
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @PostMapping(value = "/getData")
    public String getData(Model model,
                          @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                          @RequestParam(value = "limit",defaultValue = "10") int limit,
                          @RequestParam(value = "searchKey", defaultValue = "") String searchKey){
        LOGGER.info("-------------------获取数据 第"+pageNum+"页，"+limit+"条数据------------------");
        Page<RoleVo> page = PageHelper.startPage(pageNum,limit);
        List<RoleVo> list = roleService.getCommonList(searchKey);
        LOGGER.info("-------------------获取数据结束------------------");
        model.addAttribute("list",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/role/manager::list");
    }

    /**
     * 根据id更新数据
     * @param ids
     * @param type 1 启用 2 删除
     * @return
     */
    @PostMapping(value = "/updateStatus")
    @ResponseBody
    public RestResponseBo updateStatus(@RequestParam(value = "ids") String ids,
                                     @RequestParam(value = "type") String type,
                                     @RequestParam(value = "searchKey") String searchKey,
                                     HttpServletRequest request){
        LOGGER.info("-------------------修改状态------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = roleService.updateByStatus(ids,type,userVo);
        if(bool){
            String totalCount = String.valueOf(roleService.getCommonList(searchKey).size());
            return RestResponseBo.ok(totalCount,1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败，请刷新数据");
        }
    }

    /**
     * 保存角色信息 包括role 和 roleMenu
     * @param request
     * @param response
     * @param
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    @Transactional
    public RestResponseBo save(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "roleVo") String formData,
                               @RequestParam(value = "checkedNodes") String checkedNodes){
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存------------------");
        UserVo userLoginVo = TaleUtils.getLoginUser(request);
        RoleVo roleVo = new RoleVo();
        JSONObject jsonObject = JSONObject.parseObject(formData);
        roleVo.setId(jsonObject.getString("id"));
        roleVo.setName(jsonObject.getString("name"));
        roleVo.setDesc(jsonObject.getString("desc"));
        roleVo.setStatus(jsonObject.getString("status"));

        String result = roleService.save(roleVo,checkedNodes,userLoginVo);
        LOGGER.info("-------------------保存结束------------------");
        return RestResponseBo.ok(result);
    }

    /**
     * 新增编辑角色的时候 获取菜单树状图
     * @param request
     * @param response
     * @param roleId
     * @return
     */
    @PostMapping("/getTreeData")
    @ResponseBody
    public RestResponseBo getTreeData(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "id") String roleId){
        LOGGER.info("-----------------------------获取数功能菜单");
        List list = menuService.getRoleTreeData();
        List result = roleService.setCheckValue(list,roleId);
        RestResponseBo<List> dictVoRestResponseBo = new RestResponseBo<List>(true,result);
        LOGGER.info("-----------------------------获取功能菜单结束");
        return dictVoRestResponseBo;
    }


    @PostMapping("/getListRole")
    @ResponseBody
    public RestResponseBo getListRole(HttpServletRequest request,
                                      HttpServletResponse response){
        List<RoleVo> roleVos = roleService.getCommonList("");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < roleVos.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            RoleVo roleVo = roleVos.get(i);
            jsonObject.put("value",roleVo.getId());
            jsonObject.put("title",roleVo.getName());
            jsonObject.put("checked",false);
            jsonObject.put("disabled",false);
            jsonArray.add(jsonObject);
        }
        RestResponseBo restResponseBo = new RestResponseBo(true,jsonArray,"获取成功",1);
        return restResponseBo;
    }

}
