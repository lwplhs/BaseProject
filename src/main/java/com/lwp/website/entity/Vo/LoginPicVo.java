package com.lwp.website.entity.Vo;

import com.lwp.website.utils.invalid.loginPic.LoginPicValidation;
import com.lwp.website.utils.invalid.loginPic.IsNum;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/03/18:49
 * @Description:
 */
public class LoginPicVo implements Serializable {
    private static final long serialVersionUID = -2633127082759488043L;
    @NotBlank(groups = {LoginPicValidation.Edit.class},message = "该登录背景图不存在，请刷新页面重试")
    private String id;

    @NotBlank(message = "登录背景图名称不能为空")
    private String name;
    @NotBlank(message = "登录背景图详情不能为空")
    private String detail;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String createUserId;
    private String updateUserId;

    @IsNum(message = "排序值不填或者必须为大于0的数字")
    private String sort;
    @NotBlank(message = "登录背景图不能为空")
    private String attachmentId;

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
