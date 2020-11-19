package com.lwp.website.dao;

import com.lwp.website.entity.Vo.RoleMenuVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/17/18:53
 * @Description:
 */
public interface RoleMenuDao {


    int deleteByRoleId(String roleId);

    int insert(RoleMenuVo roleMenuVo);

    List<RoleMenuVo> getListByRoleId(String roleId);


}
