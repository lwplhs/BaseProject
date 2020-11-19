package com.lwp.website.dao;

import com.lwp.website.entity.Vo.UserRoleVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/18/20:25
 * @Description:
 */
public interface UserRoleDao {

    int insert(UserRoleVo userRoleVo);

    int deleteByUserId(String userId);

    List<UserRoleVo> getListByUserId(String userId);

}
