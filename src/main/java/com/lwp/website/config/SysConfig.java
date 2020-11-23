package com.lwp.website.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/15/10:58
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "website")
@PropertySource(value = "config.properties")
public class SysConfig {

    private String personal_url;

    private String upload_path;

    private String attachmentPath;

    private String upload_error_path;

    private String defaultCookie;

    private String loginErrorCount;

    private String loginUser;

    private String pubKey;

    private String userSession;

    private int shiroRedisSaveTime;

    private String loginPic;

    private String defaultRoleId;

    private String defaultPwd;

    public String getDefaultPwd() {
        return defaultPwd;
    }

    public void setDefaultPwd(String defaultPwd) {
        this.defaultPwd = defaultPwd;
    }

    public String getDefaultRoleId() {
        return defaultRoleId;
    }

    public void setDefaultRoleId(String defaultRoleId) {
        this.defaultRoleId = defaultRoleId;
    }

    public String getMouldPath() {
        return mouldPath;
    }

    public void setMouldPath(String mouldPath) {
        this.mouldPath = mouldPath;
    }

    private String mouldPath;

    public int getShiroRedisSaveTime() {
        return shiroRedisSaveTime;
    }

    public void setShiroRedisSaveTime(int shiroRedisSaveTime) {
        this.shiroRedisSaveTime = shiroRedisSaveTime;
    }

    public String getUserSession() {
        return userSession;
    }

    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }

    public String getPersonal_url() {
        return personal_url;
    }

    public void setPersonal_url(String personal_url) {
        this.personal_url = personal_url;
    }

    public String getUpload_path() {
        return upload_path;
    }

    public void setUpload_path(String upload_path) {
        this.upload_path = upload_path;
    }

    public String getUpload_error_path() {
        return upload_error_path;
    }

    public void setUpload_error_path(String upload_error_path) {
        this.upload_error_path = upload_error_path;
    }

    public String getDefaultCookie() {
        return defaultCookie;
    }

    public void setDefaultCookie(String defaultCookie) {
        this.defaultCookie = defaultCookie;
    }

    public String getLoginErrorCount() {
        return loginErrorCount;
    }

    public void setLoginErrorCount(String loginErrorCount) {
        this.loginErrorCount = loginErrorCount;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getLoginPic() {
        return loginPic;
    }

    public void setLoginPic(String loginPic) {
        this.loginPic = loginPic;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
}
