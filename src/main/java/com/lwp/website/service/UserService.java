package com.lwp.website.service;

import com.lwp.website.entity.Vo.UserVo;

import java.io.File;
import java.util.List;
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
    UserVo queryUserByUserName(String userName);


    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param enPwd
     * @param userVo
     * @return
     */
    Map changePwd(String oldPwd, String newPwd, String enPwd, UserVo userVo);

    List<UserVo> getCommonUserList(String searchKey);

    List<UserVo> getAdminUserList(String searchKey);

    boolean updateUser(String ids,String type,UserVo userVo);

    String saveUser(UserVo userVo,UserVo userLoginVo);

    long getCountByName(String userName,String id);

    Map<String,Object> importUsers(List<UserVo> list, File file,int cellCount,String prefix);

    /**
     * 将用户的角色id转换成角色名称显示
     * @param userVo
     * @return
     */
    String getRoleName(UserVo userVo);

    /**
     * 初始化密码
     * @return
     */
    Boolean defaultPwd(String ids);

}
