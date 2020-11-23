package com.lwp.website.service;

import com.lwp.website.entity.Bo.JsonResult;
import com.lwp.website.entity.Bo.MultipartFileParam;
import com.lwp.website.entity.Vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/17:18
 * @Description:
 */

public interface WebUploadService {
    /**
     * webUpload 上传
     * @param files
     * @param userVo
     * @return
     */
    Map Upload(MultipartFile files, UserVo userVo, String origin);

    String getPathById(String id);

    String getAbsPathById(String id);

    Map<String,String> getAttachment(String id);

    //分片上传 检验
    JsonResult checkshard(String fileMd5);
    //分片上传 文件 isMultipart 是否是文件上传请求
    Boolean uploadZone(MultipartFileParam files, Boolean isMultipart);
    //分片上传完成后 合并分片
    String fileMergeZone(String fileName,String fileMd5,UserVo userVo);
}
