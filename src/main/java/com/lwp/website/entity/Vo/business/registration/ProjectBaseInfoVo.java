package com.lwp.website.entity.Vo.business.registration;

/**
 * Created with IntelliJ IDEA.
 * 报名表子表-项目基本信息表
 * @Auther: liweipeng
 * @Date: 2020/11/21/20:54
 * @Description:
 */
public class ProjectBaseInfoVo {

    private String id;

    private String masterId;

    private String no;

    private String name;

    private String patentNo;

    private String inventor;

    private String stage;

    private String introduction;

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

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
