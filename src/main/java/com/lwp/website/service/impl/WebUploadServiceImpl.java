package com.lwp.website.service.impl;

import com.lwp.website.dao.AttachmentDao;
import com.lwp.website.entity.Bo.JsonResult;
import com.lwp.website.entity.Bo.MultipartFileParam;
import com.lwp.website.entity.Vo.AttachmentVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.WebUploadService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.UUID;
import com.lwp.website.utils.UploadUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.*;

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
            Map map = UploadUtil.getCarouselPath(suffix);
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

        if(StringUtil.isNull(id) || "0".equals(id)){
            String errPath = UploadUtil.getUploadErrorPicPath();
            path = errPath;
        }else {
            path = attachmentDao.getPathById(id);
            if (StringUtil.isNull(path)){
                String errPath = UploadUtil.getUploadErrorPicPath();
                path = errPath;
            }
        }
        return path;
    }

    @Override
    public String getAbsPathById(String id) {
        String path = attachmentDao.getPathById(id);
        return path;
    }

    @Override
    public Map<String,String> getAttachment(String id) {

        AttachmentVo attachmentVo = attachmentDao.getAttachmentById(id);
        String name = attachmentVo.getOldName();
        String path = attachmentVo.getSavePath();
        Map<String,String> map = new HashMap();
        map.put("name",name);
        map.put("path",path);

        return map;

    }


    @Override
    public JsonResult checkshard(String fileMd5) {
        JsonResult jr =  new JsonResult();
        String path = UploadUtil.getAttachmentPath();
        // 读取目录里的所有文件
        File dir = new File(path + "/" + fileMd5);
        File[] childs = dir.listFiles();
        if(childs==null){
            jr.setResultCode(0);//文件没有上传过，下标为零
        }else{
            jr.setResultCode(childs.length-1);//文件上传中断过，返回当前已经上传到的下标
        }
        return jr;
    }

    @Override
    public Boolean uploadZone(MultipartFileParam param, Boolean isMultipart) {
        String finalDirPath = UploadUtil.getAttachmentPath();
        Boolean result = false;
        if (isMultipart) {
            String file_name = param.getName();
            int xia_biao = param.getChunk();
            File file = new File(finalDirPath + "/" + param.getMd5());
            if (!file.exists()) {
                file.mkdir();
            }
            File chunkFile = new File(
                    finalDirPath + "/" +  param.getMd5() + "/" + xia_biao);
            try{
                FileUtils.copyInputStreamToFile(param.getFile().getInputStream(), chunkFile);
                result = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public String fileMergeZone(String fileName, String fileMd5,UserVo userVo) {
        FileChannel outChannel = null;
        String finalDirPath = UploadUtil.getAttachmentPath();
        String path = "";
        String url = "";
        String realName = "";
        String suffix = "";
        String size = "";
        Boolean result = false;

        try {
            // 读取目录里的所有文件
            File dir = new File(finalDirPath + "/" + fileMd5);
            File[] childs = dir.listFiles();
            // 转成集合，便于排序
            List<File> fileList = new ArrayList<File>(Arrays.asList(childs));
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                        return -1;
                    }
                    return 1;
                }
            });
            String date = StringUtil.getDate(new Date(),"yyyyMMdd");
            //获取文件后缀
            if(fileName.contains(".")){
                suffix=fileName.substring(fileName.lastIndexOf("."),fileName.length());
            }
            Map<String,String> map = UploadUtil.getPathMap(suffix);
            // 合并后的文件
            path = map.get("path").toString();
            url = map.get("url").toString();
            realName = map.get("name").toString();
            File outputFile = new File(path);
            if(!outputFile.getParentFile().exists()){
                outputFile.getParentFile().mkdirs();
            }
            // 创建文件
            if(outputFile.exists()){
                LOGGER.info("分片上传结束后创建文件");
                outputFile.createNewFile();
            }
            outChannel = new FileOutputStream(outputFile).getChannel();
            FileChannel inChannel = null;
            try {
                for (File file : fileList) {
                    inChannel = new FileInputStream(file).getChannel();
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                    inChannel.close();
                    // 删除分片
                    file.delete();
                }
                result = true;
            }catch (Exception e){
                e.printStackTrace();
                //发生异常，文件合并失败 ，删除创建的文件
                outputFile.delete();
                dir.delete();//删除文件夹
            }finally {
                if(inChannel!=null){
                    inChannel.close();
                }
            }
            dir.delete(); //删除分片所在的文件夹
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(outChannel!=null){
                    outChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String returnAId = "";//附件id
        //合并完成后 存放到附件表信息
        if(result){
            AttachmentVo attachmentVo = new AttachmentVo();
            attachmentVo.setId(UUID.createID());
            attachmentVo.setSaveName(realName);
            attachmentVo.setOldName(fileName);
            attachmentVo.setSavePath(path);
            attachmentVo.setUrlPath(url);
            attachmentVo.setTime(new Date());
            attachmentVo.setUserId(userVo.getId().toString());
            attachmentVo.setType("2");
            attachmentVo.setSuffix(suffix);
            attachmentVo.setSize(size);
            //保存信息
            int num = attachmentDao.insertAttachment(attachmentVo);
            if(num > 0){
                LOGGER.info("保存成功");
                returnAId = attachmentVo.getId();
            }
        }
        return returnAId;
    }
}
