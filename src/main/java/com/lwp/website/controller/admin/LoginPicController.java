package com.lwp.website.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.LoginPicVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.LoginPicService;
import com.lwp.website.utils.InvalidUtil;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.invalid.loginPic.LoginPicValidation;
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
import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/06/18:00
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin/loginPic")
public class LoginPicController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPicController.class);

    @Resource
    private LoginPicService loginPicService;

    /**
     * 添加登录背景图
     *
     * @param request
     * @param response
     * @param loginPicVo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "saveLoginPic")
    @ResponseBody
    public RestResponseBo SaveLoginPic(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @Valid @ModelAttribute LoginPicVo loginPicVo,
                                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存登录背景图------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = loginPicService.saveLoginPic(loginPicVo,userVo);
        LOGGER.info("-------------------结束保存登录背景图------------------");
        return RestResponseBo.ok(result);
    }

    /**
     * 更新登录背景图
     * @param request
     * @param response
     * @param loginPicVo
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "updateLoginPic")
    @ResponseBody
    public RestResponseBo UpdateLoginPic(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @Validated(LoginPicValidation.Edit.class) @ModelAttribute LoginPicVo loginPicVo,
                                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始更新登录背景图------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = loginPicService.saveLoginPic(loginPicVo,userVo);
        LOGGER.info("-------------------结束更新登录背景图------------------");
        return RestResponseBo.ok(result);
    }


    /**跳转到登录背景图列表 -不查询数据 不查询总数 为0的话不显示分页 显示无数据图片
     *
     * @return
     */
    @GetMapping(value = "/manager")
    public String toPagePictureList(){
        return this.render("/admin/loginPic/manager");
    }

    /**
     * 获取 登录背景图 总数
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/getTotalCount")
    public String getTotalCount(){
        String totalCount = loginPicService.getTotalCount();

        return totalCount;
    }

    /**
     * 查看页面
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/view")
    public String toPageLoginPicView(Model model,
                                     @RequestParam(value = "id",defaultValue = "") String id){
        LoginPicVo loginPicVo = loginPicService.getLoginPicById(id);
        model.addAttribute("LoginPic",loginPicVo);
        return this.render("/admin/loginPic/view");
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @GetMapping(value = "/add")
    public String toPageLoginPicAdd(){
        return this.render("/admin/loginPic/add");
    }
    /**
     * 跳转到更新页面 -根据id查询数据填充
     * @param model
     * @param id
     * @return
     */
    @GetMapping(value = "/edit")
    public String toPageLoginPicEdit(Model model,
                                     @RequestParam(value = "id",defaultValue = "") String id){
        LoginPicVo loginPicVo = loginPicService.getLoginPicById(id);
        model.addAttribute("LoginPic",loginPicVo);
        return this.render("/admin/loginPic/edit");
    }
    /**
     * 根据分页 获取登录背景图数据
     * @param model
     * @param pageNum
     * @param limit
     * @return
     */
    @PostMapping(value = "/getLoginPic")
    public String getLoginPic(Model model,
                              @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "limit",defaultValue = "10") int limit){
        LOGGER.info("-------------------获取登录背景图数据 第"+pageNum+"页，"+limit+"条数据------------------");
        Page<LoginPicVo> page = PageHelper.startPage(pageNum,limit);
        List<LoginPicVo> list = loginPicService.getListLoginPic();
        LOGGER.info("-------------------获取登录背景图数据结束------------------");
        model.addAttribute("picList",list);
        model.addAttribute("total",page.getTotal());
        return this.render("/admin/loginPic/manager::list");
    }

    /**
     * 根据id更新数据
     * @param ids
     * @param type 1 状态status
     * @return
     */
    @PostMapping(value = "/updateLoginPicStatus")
    @ResponseBody
    public RestResponseBo updateCarouse(@RequestParam(value = "ids") String ids,
                                        @RequestParam(value = "type") String type,
                                        HttpServletRequest request){
        LOGGER.info("-------------------修改登录背景图数据------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = loginPicService.updateLoginPic(ids,type,userVo);
        if(bool){
            String totalCount = loginPicService.getTotalCount();
            return RestResponseBo.ok(totalCount,1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败，请刷新数据");
        }
    }
}



