package com.lwp.website.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lwp.website.config.SysConfig;
import com.lwp.website.entity.Bo.Sn;
import com.lwp.website.utils.MacUtil;
import com.lwp.website.utils.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/06/14/15:14
 * @Description:
 */
@Component
public class ValidSn {
    @Autowired
    private SysConfig sysConfig;
    private Logger LOOGER = LoggerFactory.getLogger(ValidSn.class);
    public final static String UTF8 = "utf-8";
    private static final String KEY_ALGORITHM = "RSA";
    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    private String formatString(String source) {
        if (source == null) {
            return null;
        }
        return source.replaceAll("\\r", "").replaceAll("\\n", "");
    }

    private String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
    /**
     * 公钥解密
     *
     * @param data      解密数据
     * @param publicKey 公钥
     * @return
     */
    private String decryptByPublicKey(String data, String publicKey) {
        try {
            publicKey = formatString(publicKey);
            byte[] kb = Base64.decodeBase64(publicKey.getBytes(UTF8));
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(kb);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            //Cipher cipher = Cipher.getInstance(RSA_PADDING_KEY);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] b = data.getBytes(UTF8);
            byte[] encryptedData = Base64.decodeBase64(b);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cashe;
            int i = 0;
            //对数据分段加密
            while (inputLen - offSet > 0){
                if(inputLen - offSet > MAX_DECRYPT_BLOCK){
                    cashe = cipher.doFinal(encryptedData,offSet,MAX_DECRYPT_BLOCK);
                }else {
                    cashe = cipher.doFinal(encryptedData,offSet,inputLen - offSet);
                }
                out.write(cashe,0,cashe.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            //cipher.doFinal(Base64.decodeBase64(b))
            //Base64.decodeBase64(b)
            byte[] decrypt = out.toByteArray();
            out.close();
            return new String(decrypt, UTF8);
        } catch (Exception e) {
            //logger.error("Failed to decryptByPublicKey data.", e);
            throw new RuntimeException();
        }
    }

    @Bean(name = "sn")
    public Sn Sn(){



        LOOGER.info("检查授权文件......");
        Sn sn = new Sn();
        sn.setName("123");
        sn.setExTime("1");
        return sn;
//        String pubKey = sysConfig.getPubKey();
//        String path = this.getUploadFilePath();
//        try {
//            String data="";
//            String s = "";
//            Sn sn = new Sn();
//            JSONObject jsonObject;
//            try {
//                data = this.readFileContent(path);
//                s = this.decryptByPublicKey(data, pubKey);
//                jsonObject = JSONObject.parseObject(s);
//            }catch (Exception e){
//                throw  new Exception();
//            }
//            Boolean isMac = true;
//            try{
//                isMac = StringUtil.isNull(jsonObject.getBoolean("isMac"))?true:jsonObject.getBoolean("isMac");
//            }catch (Exception e){
//                isMac = true;
//            }
//            String mac = "";
//            String ex = "";
//            String name = "";
//            String concurrent = "";
//            String code = "";
//            try {
//                mac = jsonObject.getString("mac");
//                ex = jsonObject.getString("ex");
//                name = jsonObject.getString("name");
//                concurrent = jsonObject.getString("concurrent");
//                code = jsonObject.getString("code");
//            }catch (Exception e){
//                throw new Exception();
//            }
//            if(!StringUtil.isNull(name)){
//                LOOGER.info("用户名称："+name);
//            }
//            if(!StringUtil.isNull(code)){
//                LOOGER.info("用户编码："+code);
//            }
//            if(StringUtil.isNull(isMac) || isMac) {
//                List<String> macList = this.getMac();
//                Boolean bool = false;
//                if (!StringUtil.isNull(macList) && macList.size() > 0 && !StringUtil.isNull(mac)) {
//                    for (int i = 0; i < macList.size() && !bool; i++) {
//                        String localMac = macList.get(i);
//                        if (MD5encode(localMac.toLowerCase()).equals(mac)) {
//                            bool = true;
//                        }
//                    }
//                }
//                if (!bool) {
//                    LOOGER.error("授权文件mac地址校验错误");
//                    throw new Exception();
//                }
//            }
//            //本机mac地址
//            try {
//                Date date = null;
//                Date nowDate = new Date();
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                date = formatter.parse(ex);
//                LOOGER.info("到期时间为：" + ex);
//                if (nowDate.after(date)) {
//                    LOOGER.error("授权文件已到期！");
//                    throw new Exception();
//                }
//            }catch (Exception e){
//                throw new Exception();
//            }
//            if(StringUtil.isNull(concurrent)){
//                throw new Exception();
//            }else {
//                LOOGER.info("并发许可:" + concurrent);
//            }
//            LOOGER.info("授权文件检查成功");
//            sn.setName(name);
//            sn.setExTime(ex);
//            return sn;
//        }catch (Exception e){
//            LOOGER.error("授权文件校验失败！请联系获取最新授权文件");
//            throw new RuntimeException();
//        }
    }

    /**
     * 获取classes的绝对路径
     *
     * @return
     */
    private String getUploadFilePath() {
        String path = System.getProperty("user.dir");
        path = path.replaceAll("\\\\","/");
        if(path.endsWith("/")) {
            int lastIndex = path.lastIndexOf("/") + 1;
            path = path.substring(0, lastIndex);
        }
        path = path+"/config/sn";
        File file = new File(path);
        return file.getAbsolutePath();
    }
    /**
     * md5加密
     *
     * @param source 数据源
     * @return 加密字符串
     */
    private static String MD5encode(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] encode = messageDigest.digest(source.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte anEncode : encode) {
            String hex = Integer.toHexString(0xff & anEncode);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * 获取本机mac地址
     * @return
     */
    private static List getMac(){
        List<String> list = new ArrayList();
        try {
            /*Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                StringBuffer stringBuffer = new StringBuffer();
                NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
                if (networkInterface != null) {
                    byte[] bytes = networkInterface.getHardwareAddress();
                    if (bytes != null) {
                        for (int i = 0; i < bytes.length; i++) {
                            if (i != 0) {
                                stringBuffer.append("-");
                            }
                            int tmp = bytes[i] & 0xff; // 字节转换为整数
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) {
                                stringBuffer.append("0" + str);
                            } else {
                                stringBuffer.append(str);
                            }
                        }
                        String mac = stringBuffer.toString().toUpperCase();
                        list.add(mac);
                    }
                }
            }*/
            list = MacUtil.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

}
