package com.lwp.website.service;

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
}
