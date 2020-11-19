package com.lwp.website.entity.Vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/17/18:48
 * @Description:
 */
public class RoleMenuVo implements Serializable {


    private static final long serialVersionUID = -2542763718387670164L;
    private String id;

    private String roleId;

    private String menuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
