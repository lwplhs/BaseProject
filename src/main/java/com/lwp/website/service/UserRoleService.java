package com.lwp.website.service;

import com.lwp.website.entity.Vo.UserVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/18/20:32
 * @Description:
 */
public interface UserRoleService {

    void insertData(UserVo userVo);


    List<String> getRoleIdByUserId(String userId);


}
