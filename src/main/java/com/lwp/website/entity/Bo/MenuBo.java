package com.lwp.website.entity.Bo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/16/10:13
 * @Description:
 */
public class MenuBo {

    private String id;
    private String name;
    private String icon;
    private String url;
    private List<MenuBo> subMenuList;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuBo> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<MenuBo> subMenuList) {
        this.subMenuList = subMenuList;
    }
}
