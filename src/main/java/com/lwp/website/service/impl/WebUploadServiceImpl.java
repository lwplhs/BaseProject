package com.lwp.website.service.impl;

import com.lwp.website.dao.AttachmentDao;
import com.lwp.website.entity.Vo.AttachmentVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.WebUploadService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/22/17:29
 * @Description:
 */
@Service
public class WebUploadServiceImpl implements WebUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUploadService.class);

    @Resource
    private AttachmentDao attachmentDao;

    /**
     * webUpload 文件上传
     * @param files
     * @param user
     * @param origin
     * @return
     */
    @Override
    public Map Upload(MultipartFile files, UserVo user, String origin) {
        Map result = new HashMap();
        boolean b = false;
        //绝对路径
        String path = "";
        //相对路径
        String url = "";
        //文件名称
        String realName = "";
        //文件后缀
        String suffix = "";
        //文件上传时的名称
        String name = "";
        //保存上传文件
        String size = "";
        //图片高宽
        String width = "";
        String height = "";
        try{
            name = files.getOriginalFilename();
            suffix = name.substring(name.lastIndexOf("."));
            Map map = TaleUtils.getCarouselPath(suffix);
            path = map.get("path").toString();
            url = map.get("url").toString();
            realName = map.get("name").toString();
            File uploadFile = new File(path);
            if(!uploadFile.getParentFile().exists()){
                uploadFile.mkdirs();
            }
            size = String.valueOf(files.getSize());
            files.transferTo(uploadFile);
            b = true;
        }catch(Exception e){
            b = false;
            result.put("code","111111");
            result.put("msg","上传失败");
            e.printStackTrace();
        }
        //保存上传文件信息到数据库中
        if(b){
            AttachmentVo attachmentVo = new AttachmentVo();
            attachmentVo.setId(UUID.createID());
            attachmentVo.setSaveName(realName);
            attachmentVo.setOldName(name);
            attachmentVo.setSavePath(path);
            attachmentVo.setUrlPath(url);
            attachmentVo.setTime(new Date());
            attachmentVo.setUserId(user.getId().toString());
            attachmentVo.setType(origin);
            attachmentVo.setSuffix(suffix);
            attachmentVo.setSize(size);
            try {
                File picture=new File(path);
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(picture)); // 通过MultipartFile得到InputStream，从而得到BufferedImage
                if (bufferedImage != null) {
                    LOGGER.info("获取图片的高宽");
                    width = String.valueOf(bufferedImage.getWidth());
                    height = String.valueOf(bufferedImage.getHeight());
                    attachmentVo.setWidth(width);
                    attachmentVo.setHeight(height);
                }
                // 省略逻辑判断
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
            //保存信息
            int num = attachmentDao.insertAttachment(attachmentVo);
            if(num > 0){
                LOGGER.info("保存成功");
            }
            result.put("path",path);
            result.put("url",url);
            result.put("aid",attachmentVo.getId());
            result.put("code","100000");
            result.put("msg","上传成功");
        }
        return result;
    }

    /**
     * 根据id获取附件路径
     * @param id
     * @return
     */
    @Override
    public String getPathById(String id) {
        String path = "";
        String errPath = TaleUtils.getUploadErrorPicPath();
        if(StringUtil.isNull(id) || "0".equals(id)){
            path = errPath;
        }else {
            path = attachmentDao.getPathById(id);
            if (StringUtil.isNull(path)){
                path = errPath;
            }
        }
        return path;
    }
}
