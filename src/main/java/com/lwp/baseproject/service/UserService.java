package com.lwp.baseproject.service;

import com.lwp.baseproject.entity.Vo.UserVo;

import java.util.Map;

public interface UserService {

    /**
     * 保存用户数据
     * @param userVo
     * @return
     */
    Integer insertUser(UserVo userVo);

    /**
     * 通过uid查找对象
     * @param uid
     * @return
     */
    UserVo queryUserById(String uid);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    UserVo login(String username, String password);

    /**
     * 根据主键更新user对象
     * @param userVo
     */
    void updateByUid(UserVo userVo);

    /**
     * 根据用户名查找User
     * @param userName
     * @return
     */
    UserVo queryUserNyUserName(String userName);


    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param enPwd
     * @param userVo
     * @return
     */
    Map changePwd(String oldPwd, String newPwd, String enPwd, UserVo userVo);

}
