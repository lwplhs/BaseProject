package com.lwp.website.controller.admin.webupload;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.config.SysConfig;
import com.lwp.website.entity.Bo.JsonResult;
import com.lwp.website.entity.Bo.MultipartFileParam;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.WebUploadService;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.UploadUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/17:13
 * @Description: webUpload 控制类
 */
@Controller("webUploadController")
@RequestMapping(value = "/admin/webUpload")
public class WebUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUploadController.class);

    @Resource
    private SysConfig sysConfig;


    @Resource
    private WebUploadService webUploadService;

    /**
     * webupload上传图片方法
     * @param files MultipartFile
     * @param origin 来源 0 未知来源 1 首页轮播图 2：商品类别
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public String webUpload(@RequestParam("file") MultipartFile files,
                                 @RequestParam(value = "origin",defaultValue = "0") String origin,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        JSONObject json=new JSONObject();
        response.setCharacterEncoding("utf-8");
        LOGGER.info("-------------------开始调用上传文件upload接口-------------------");
        String s = sysConfig.getUpload_path();
        UserVo userVo = TaleUtils.getLoginUser(request);
        Map map = webUploadService.Upload(files,userVo,origin);
        LOGGER.info("-------------------结束调用上传文件upload接口-------------------");
        json = (JSONObject) JSONObject.toJSON(map);
        return json.toString();
    }

    /**
     * 根据附件id获取附件路径
     * @param id 附件ID
     * @return 附件路径
     */
    @PostMapping(value = "/path")
    @ResponseBody
    public String getWebUploadPath(@RequestParam(value = "id",defaultValue = "0") String id){
        LOGGER.info("-----------------开始获取附加路径");
        String path = webUploadService.getPathById(id);
        LOGGER.info("-----------------结束获取附件路径");
        return path;
    }

    @RequestMapping("/downloadMould")
    public void downloadMould(HttpServletRequest request,
                              HttpServletResponse response,String name) throws IOException {
        Map map = UploadUtil.getMouldPath();
        String path = map.get("path").toString() +name;
        File file = new File(path);
        UploadUtil.downFile(request,response,name,file);
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request,
                             HttpServletResponse response, String aId) throws IOException{
        Map<String,String> map =  webUploadService.getAttachment(aId);
        String name = map.get("name");
        String path = map.get("path");
        File file = new File(path);
        UploadUtil.downFile(request,response,name,file);
    }

    /**
     * 判断文件是否上传过，是否存在分片，断点续传
     */
    @RequestMapping(value = "/checkshard", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkshard(HttpServletRequest request, HttpServletResponse response) {
        String fileMd5 = request.getParameter("fileMd5");
        LOGGER.info("判断文件是否上传过，是否存在分片，断点续传");

        JsonResult jr = webUploadService.checkshard(fileMd5);
        if(jr.getResultCode() == 0){
            LOGGER.info("判断结束，该文件未上传过");
        }else {
            LOGGER.info("改文件存在分片，断点重传");
        }
        return  jr;
    }

    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/filewebUpload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult filewebUpload(MultipartFileParam param, HttpServletRequest request) {
        LOGGER.info("开始分片上传");

        JsonResult jr = new JsonResult();
        Boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        Boolean result = webUploadService.uploadZone(param,isMultipart);
        if(result) {
            jr.setResultMsg("上传成功");
        }else {
            jr.setResultMsg("上传失败，请刷新页面重试");
        }
        LOGGER.info("分片上传结束");
        return jr;
    }

    /**
     * 分片上传成功之后，合并文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/filewebMerge", method = RequestMethod.POST)
    @ResponseBody
    public RestResponseBo filewebMerge(HttpServletRequest request, HttpServletResponse response) {

        String fileName = request.getParameter("fileName");
        String fileMd5 = request.getParameter("fileMd5");
        UserVo userVo = TaleUtils.getLoginUser(request);
        String aId = webUploadService.fileMergeZone(fileName,fileMd5,userVo);
        if(StrUtil.isNotEmpty(aId)){
            LOGGER.info("分片合并成功");
        }else {
            LOGGER.info("分片合成失败");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aName",fileName);
        jsonObject.put("aId",aId);

        RestResponseBo restResponseBo = new RestResponseBo(true,jsonObject);
        return restResponseBo;
    }

}
