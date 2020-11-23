package com.lwp.website.entity.Vo.business.registration;

/**
 * Created with IntelliJ IDEA.
 * 报名表子表 介绍资料表
 * @Auther: liweipeng
 * @Date: 2020/11/21/21:00
 * @Description:
 */
public class IntroductionVo {

    private String id;

    private String masterId;

    private String no;

    private String name;

    private String attachmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }
}
