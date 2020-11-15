package com.lwp.website.entity.Vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/15/14:54
 * @Description:
 */
public class MenuVo implements Serializable {

    private static final long serialVersionUID = -9180832562094347763L;
    private String id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 链接
     */
    private String url;
    /**
     * 状态
     */
    private String status;
    /**
     * 排序
     */
    private String sort;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
