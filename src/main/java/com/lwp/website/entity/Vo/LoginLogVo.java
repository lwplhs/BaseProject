package com.lwp.website.entity.Vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/27/16:39
 * @Description:
 */
public class LoginLogVo implements Serializable {
    private static final long serialVersionUID = 1697896466587644878L;
    private String id;
    private String userName;
    private Long loginTime;
    private String loginResult;
    private String loginResultDetail;
    private String loginUrl;

    public LoginLogVo(){

    }

    public LoginLogVo(String id, String userName, String loginResult, String loginResultDetail, String loginUrl){
        this.id = id;
        this.userName = userName;
        this.loginResult = loginResult;
        this.loginResultDetail = loginResultDetail;
        this.loginUrl = loginUrl;
        this.loginTime = new Date().getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLoginResultDetail() {
        return loginResultDetail;
    }

    public void setLoginResultDetail(String loginResultDetail) {
        this.loginResultDetail = loginResultDetail;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
