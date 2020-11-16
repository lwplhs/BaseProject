package com.lwp.website.entity.Vo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/15/14:54
 * @Description:
 */
public class MenuVo implements Serializable {

    private static final long serialVersionUID = -3224277607966090666L;
    private String id;

    /**
     * 上级id
     */
    private String pid;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不为空")
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
    private int sort;

    private String series;

    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
