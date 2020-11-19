package com.lwp.website.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.entity.Vo.LoginPicVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.UserService;
import com.lwp.website.utils.ImportExcel;
import com.lwp.website.utils.InvalidUtil;
import com.lwp.website.utils.TaleUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/08/17:54
 * @Description:
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {


    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    /**跳转到用户管理列表 -不查询数据 不查询总数 为0的话不显示分页 显示无数据图片
     *
     * @return
     */
    @GetMapping(value = "/manager")
    public String toPageManager(){
        return this.render("/admin/user/manager");
    }

    @GetMapping(value = "/add")
    public String toPageAdd(Model model){
        //普通用户
        UserVo userVo = new UserVo();
        userVo.setType("0");
        model.addAttribute("user",userVo);
        model.addAttribute("action","add");
        return this.render("/admin/user/add");
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
        return this.render("/admin/user/add");
    }

    @GetMapping(value = "/import")
    public String toPageImport(){
        return this.render("/admin/user/import");
    }



    /**
     * 根据分页 获取数据
     * 增加关键字查询
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @PostMapping(value = "/getUser")
    public String getUser(Model model,
                              @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "limit",defaultValue = "10") int limit,
                              @RequestParam(value = "searchKey", defaultValue = "") String searchKey){
        LOGGER.info("-------------------获取用户数据 第"+pageNum+"页，"+limit+"条数据------------------");
        Page<UserVo> page = PageHelper.startPage(pageNum,limit);
        List<UserVo> list = userService.getCommonUserList(searchKey);
        LOGGER.info("-------------------获取登用户数据结束------------------");
        model.addAttribute("userList",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/user/manager::list");
    }

    /**
     * 根据id更新数据
     * @param ids
     * @param type 1 启用 2 删除
     * @return
     */
    @PostMapping(value = "/updateUserStatus")
    @ResponseBody
    public RestResponseBo updateUser(@RequestParam(value = "ids") String ids,
                                        @RequestParam(value = "type") String type,
                                        @RequestParam(value = "searchKey",defaultValue = "") String searchKey,
                                        HttpServletRequest request){
        LOGGER.info("-------------------修改用户状态------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = userService.updateUser(ids,type,userVo);
        if(bool){
            String totalCount = String.valueOf(userService.getCommonUserList(searchKey).size());
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
    @PostMapping("/saveUser")
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
        LOGGER.info("-------------------开始保存用户------------------");
        UserVo userLoginVo = TaleUtils.getLoginUser(request);
        String result = userService.saveUser(userVo,userLoginVo);
        LOGGER.info("-------------------保存用户结束------------------");
        return RestResponseBo.ok(result);
    }

    @PostMapping("/importUser")
    @ResponseBody
    public RestResponseBo importUser(HttpServletRequest request, HttpServletResponse response, MultipartFile file){
        File file1 = null;
        try {
            InputStream inputStream = null;
            String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            file1 = File.createTempFile("temp", null);
            file.transferTo(file1);
            inputStream =new FileInputStream(file1);

            String[] excelHead={"登录名称","用户名","密码","电话","电子邮箱","身份证号码","住址"};
            String[] excelHeadAlias={"username","screenName","password","telephone","email","identification","address"};
            List<UserVo> list= ImportExcel.importExcel(inputStream,excelHead,excelHeadAlias,UserVo.class);
            Map result = userService.importUsers(list,file1,7,prefix);

            if("1".equals(result.get("errCode"))){
                return RestResponseBo.ok(1,result.get("errMsg"));
            }else if("-1".equals(result.get("errCode"))){
                return RestResponseBo.fail(-1,result.get("errMsg").toString());
            }else if("-2".equals(result.get("errCode"))){
                if("1".equals(result.get("errFileCode"))){
                    return RestResponseBo.fail(result.get("errFile"),-2,result.get("errMsg").toString());
                }else {
                    return RestResponseBo.fail(-1,result.get("errMsg").toString());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            file1.deleteOnExit();
        }
        return RestResponseBo.fail(-3);
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
