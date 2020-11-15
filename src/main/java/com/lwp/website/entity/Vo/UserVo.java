package com.lwp.website.entity.Vo;

import com.lwp.website.utils.invalid.user.IsRepeatUserName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2019/12/20/10:19
 * @Description:
 */
@IsRepeatUserName(vId = "id",vName = "username",vPassword = "password",message = "登录名称已存在")
public class UserVo implements Serializable {
    private static final long serialVersionUID = 4462234041195569332L;
    /**
     * user表主键
     */
    private String id;
    /**
     * 用户名称
     */
    @NotBlank(message = "登录名称不能为空")
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户主页
     */
    private String homeUrl;

    /**
     * 用户显示的名称
     */
    @NotBlank(message = "用户名不能为空")
    private String screenName;

    /**
     * 用户注册时的GMT unix时间戳
     */
    private Integer created;

    /**
     * 最后活动时间
     */
    private Integer activated;

    /**
     * 上次登录最后活动时间
     */
    private Integer logged;

    /**
     * 用户组
     */
    private String groupName;

    /**
     * 状态 0 正常 1 未启用 2删除
     */
    private String status;

    /**
     * 账号类型 0 普通账户 1管理员账号
     */
    private String type;

    /**
     * 身份证号码
     */
    private String identification;

    /**
     * 住址
     */
    private String address;

    /**
     * 单位
     */
    private String unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    public Integer getLogged() {
        return logged;
    }

    public void setLogged(Integer logged) {
        this.logged = logged;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
