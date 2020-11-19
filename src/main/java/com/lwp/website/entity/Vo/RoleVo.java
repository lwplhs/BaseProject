package com.lwp.website.entity.Vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/16/17:59
 * @Description:
 */
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -5796144406690246967L;
    private String id;

    private String name;

    private String desc;

    private String status;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
