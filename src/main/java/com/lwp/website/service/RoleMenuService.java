package com.lwp.website.service;

import com.lwp.website.entity.Vo.RoleMenuVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/17/19:11
 * @Description:
 */
public interface RoleMenuService {

    int save(String data,String roleId);

    List<RoleMenuVo> getList(String roleId);

}
