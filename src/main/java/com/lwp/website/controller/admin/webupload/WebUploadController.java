package com.lwp.website.controller.admin.webupload;

import com.alibaba.fastjson.JSONObject;
import com.lwp.website.config.SysConfig;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.WebUploadService;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

}
