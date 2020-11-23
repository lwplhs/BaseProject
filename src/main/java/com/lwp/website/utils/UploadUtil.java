package com.lwp.website.utils;

import cn.hutool.core.util.StrUtil;
import com.lwp.website.config.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *  上传下载工具类
 * @Auther: liweipeng
 * @Date: 2020/11/14/11:44
 * @Description:
 */
@Component
public class UploadUtil {

    private static SysConfig sysConfig;

    @Autowired
    public void setSysConfig(SysConfig sysConfig){
        UploadUtil.sysConfig = sysConfig;
    }

    public static String GetImageStr(String imgFile) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    /**
     * 下载文件到浏览器
     * @param request
     * @param response
     * @param filename 要下载的文件名
     * @param file     需要下载的文件对象
     * @throws IOException
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response, String filename, File file) throws IOException {
        //  文件存在才下载
        if (file.exists()) {
            OutputStream out = null;
            FileInputStream in = null;
            try {
                // 1.读取要下载的内容
                in = new FileInputStream(file);

                // 2. 告诉浏览器下载的方式以及一些设置
                // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefo要求是iso-8859-1编码
                String agent = request.getHeader("user-agent");
                if (agent.contains("FireFox")) {
                    filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                } else {
                    filename = URLEncoder.encode(filename, "UTF-8");
                }
                // 设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(filename);
                response.setContentType(mineType);
                // 设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                // 将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
        }
    }

    public static Map getMouldPath(){
        Map map = new HashMap();
        String content = "";
        try {
            content = sysConfig.getMouldPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(StringUtil.isNull(content)){
            content = "/media/mould";
        }
        String path = System.getProperty("user.dir");
        String url = content;
        path = path.replaceAll("\\\\","/");


        url = addSeparator(0,"/",url);
        url = addSeparator(1,"/",url);

        url = addSeparator(1,"/",url);
        map.put("path",path+url);
        map.put("url",url);
        return map;
    }

    /**
     * 获取保存文件的位置,jar所在目录的路径
     *
     * @return
     */
    public static String getUploadFilePath() {
        String path = TaleUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(1, path.length());
        try {
            path = java.net.URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(0, lastIndex);
        File file = new File(path);
        return file.getAbsolutePath() + "\\";
    }

    /**
     * 获取 Ueditor 路径
     * @return
     */
    public static String getUEditorPath(){
        String path = System.getProperty("user.dir");
        path = path.replaceAll("\\\\","/");
        path = "file:"+path;
        return path;
    }



    /**
     * 获取 轮播图 保存位置
     * @param suffix 后缀
     * @return
     */
    public static Map getCarouselPath(String suffix){
        Map map = new HashMap();
        String content = "";
        try {
            content = sysConfig.getUpload_path();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(StringUtil.isNull(content)){
            content = "/media/Carousel/upload/image";
        }
        String path = System.getProperty("user.dir");
        String url = content;
        path = path.replaceAll("\\\\","/");


        url = addSeparator(0,"/",url);
        url = addSeparator(1,"/",url);

        String date = StringUtil.getDate(new Date(),"yyyyMMdd");
        url = url + date;

        url = addSeparator(1,"/",url);

        String name = UUID.UU32();
        url = url + name;

        if(!suffix.startsWith(".")){
            url = url+".";
        }
        url = url + suffix;
        if(path.endsWith("/")){
            path = path.substring(0,path.length()-2);
        }
        path = path + url;
        map.put("path",path);
        map.put("url",url);
        map.put("name",name);
        return map;
    }

    public static String getAttachmentPath(){
        Map map = new HashMap();
        String content = "";
        try {
            content = sysConfig.getAttachmentPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(StringUtil.isNull(content)){
            content = "/media/Carousel/upload/attachment";
        }
        String path = System.getProperty("user.dir");
        String url = content;
        path = path.replaceAll("\\\\","/");


        url = addSeparator(0,"/",url);

        return path+url;
    }
    //获取附件的请求路径、存放路径、名称等
    public static Map getPathMap(String suffix){
        Map map = new HashMap();
        String content = "";
        try {
            content = sysConfig.getAttachmentPath();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(StringUtil.isNull(content)){
            content = "/media/Carousel/upload/attachment";
        }
        String path = System.getProperty("user.dir");
        String url = content;
        path = path.replaceAll("\\\\","/");


        url = addSeparator(0,"/",url);
        url = addSeparator(1,"/",url);

        String date = StringUtil.getDate(new Date(),"yyyyMMdd");
        url = url + date;

        url = addSeparator(1,"/",url);

        String name = UUID.UU32();
        url = url + name;

        if(StrUtil.isNotEmpty(suffix) && !suffix.startsWith(".")){
            url = url+".";
        }
        url = url + suffix;
        if(path.endsWith("/")){
            path = path.substring(0,path.length()-2);
        }
        path = path + url;
        map.put("path",path);
        map.put("url",url);
        map.put("name",name);
        return map;
    }

    /**
     * 给字符串头和尾增加分隔符
     * @param index 0 头 1 尾
     * @param separator 分隔符
     * @param arg 字符串
     * @return
     */
    private static String addSeparator(int index,String separator,String arg){
        switch (index){
            case 0:
                if(!arg.startsWith(separator)){
                    arg = separator+arg;
                }
                break;
            case 1:
                if(!arg.endsWith(separator)){
                    arg = arg + separator;
                }
                break;
            default:
                break;
        }
        return arg;
    }

    public static String getUploadErrorPicPath(){
        String path = "";
        try {
            path = sysConfig.getUpload_error_path();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(StringUtil.isNull(path)){
            path="/static/admin/images/error.jpg";
        }
        return path;
    }

}
