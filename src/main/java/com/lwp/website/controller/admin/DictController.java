package com.lwp.website.controller.admin;

import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.DictService;
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
 * @Date: 2020/09/29/16:38
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin/dict")
public class DictController extends BaseController {

    private Logger LOGGER = LoggerFactory.getLogger(DictController.class);

    @Resource
    private DictService dictService;

    @GetMapping("/manager")
    public String manager(){
        return this.render("/admin/dict/manager");
    }

    @GetMapping("/subDetail")
    public String subDetail(){

        return this.render("/admin/dict/subDetail");
    }

    /**
     * 获取数据
     * @param id
     * @return
     */
    @PostMapping(value = "/getSubData")
    @ResponseBody
    public RestResponseBo getSubData(String id){
        LOGGER.info("-----------------------------获取数据字典分类");
        List list = dictService.getSubData(id);
        RestResponseBo<List> restResponseBo = new RestResponseBo<List>(true,list);
        return restResponseBo;
    }

    /**
     * 根据id id = 0 是首级节点
     * 跳转到新增页面
     * @param id
     * @return
     */
    @GetMapping("/add")
    public String addPage(@RequestParam(value = "id",defaultValue = "0") String id,
                          Model model){
        String lastName = "";
        String sort = "1";
        String series = "1";
        String lastId = id;
        int status = 0;
        if("0".equals(id)){
            lastName = "/";
            sort = dictService.getSort("1","");
            series = "1";
        }else {
            //根据id获取
            DictVo dictVo = dictService.getDictById(id);
            lastName = dictVo.getName();
            sort = dictService.getSort("2",id);
            series = "2";
        }
        model.addAttribute("sort",sort);
        model.addAttribute("lastName",lastName);
        model.addAttribute("series",series);
        model.addAttribute("lastId",lastId);
        model.addAttribute("status",status);
        model.addAttribute("action","new");
        return this.render("/admin/dict/add");
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id",defaultValue = "") String id,
                           Model model){

        String lastName = "";
        String sort = "1";
        String series = "1";
        String lastId = "";
        int status = 0;
        String name = "";
        String describe = "";

        DictVo dictVo = dictService.getDictById(id);
        if(null != dictVo){
            lastName = dictVo.getLastName();
            sort = dictVo.getSort();
            series = dictVo.getSeries();
            lastId = dictVo.getLastId();
            status = Integer.parseInt(dictVo.getStatus());
            name = dictVo.getName();
            describe = dictVo.getDescribe();
        }
        model.addAttribute("sort",sort);
        model.addAttribute("lastName",lastName);
        model.addAttribute("series",series);
        model.addAttribute("lastId",lastId);
        model.addAttribute("status",status);
        model.addAttribute("name",name);
        model.addAttribute("describe",describe);
        model.addAttribute("id",id);
        model.addAttribute("action","edit");
        return this.render("/admin/dict/add");

    }

    @GetMapping("/view")
    public String viewPage(@RequestParam(value = "id",defaultValue = "") String id,
                           Model model){
        String lastName = "";
        String sort = "1";
        String series = "1";
        String lastId = "";
        int status = 0;
        String name = "";
        String describe = "";

        DictVo dictVo = dictService.getDictById(id);
        if(null != dictVo){
            lastName = dictVo.getLastName();
            sort = dictVo.getSort();
            series = dictVo.getSeries();
            lastId = dictVo.getLastId();
            status = Integer.parseInt(dictVo.getStatus());
            name = dictVo.getName();
            describe = dictVo.getDescribe();
        }
        model.addAttribute("sort",sort);
        model.addAttribute("lastName",lastName);
        model.addAttribute("series",series);
        model.addAttribute("lastId",lastId);
        model.addAttribute("status",status);
        model.addAttribute("name",name);
        model.addAttribute("describe",describe);
        model.addAttribute("id",id);
        model.addAttribute("action","view");
        return this.render("/admin/dict/add");
    }


    @PostMapping(value = "/getData")
    @ResponseBody
    public RestResponseBo getData(){
        LOGGER.info("-----------------------------获取数据字典分类");
        List list = dictService.getDictList();
        RestResponseBo<List> dictVoRestResponseBo = new RestResponseBo<List>(true,list);
        LOGGER.info("-----------------------------获取数据字典分类结束");
        return dictVoRestResponseBo;
    }


    @PostMapping(value = "/saveDict")
    @ResponseBody
    public RestResponseBo saveDict(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @Validated @ModelAttribute DictVo dictVo,
                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return InvalidUtil.error(LOGGER,bindingResult);
        }
        LOGGER.info("-------------------参数校验成功------------------");
        LOGGER.info("-------------------开始保存数据字典------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String result = dictService.saveDict(dictVo,userVo);
        LOGGER.info("-------------------保存数据字典结束------------------");
        return RestResponseBo.ok(result);
    }

    @PostMapping(value = "/getSeriesById")
    @ResponseBody
    public RestResponseBo getSeriesById(String id){
        LOGGER.info("-----------------开始获取数据字典级别，id:"+id);
        String series = dictService.getSeriesById(id);
        LOGGER.info("-----------------获取数据字典级别成功-----------------");
        return RestResponseBo.ok(series,1,"获取数据字典成功");
    }

    /**
     * 根据id更新 状态 1 启用 ，停用 2 删除
     * @param type
     * @param id
     * @return
     */
    @PostMapping(value = "updateDictStatusById")
    @ResponseBody
    public RestResponseBo updateDictStatusById(@RequestParam(value = "type") String type,
                                                          @RequestParam(value = "id") String id,
                                                          HttpServletRequest request){

        LOGGER.info("-------------------修改商品分类状态开始------------------");
        UserVo userVo = TaleUtils.getLoginUser(request);
        Boolean bool = dictService.updateDictWithType(type,id,userVo);
        LOGGER.info("-------------------修改商品分类状态结束------------------");
        if(bool){
            return RestResponseBo.ok(1,"更新成功");
        }else {
            return RestResponseBo.fail(-1,"更新失败，请刷新数据!");
        }
    }

}
