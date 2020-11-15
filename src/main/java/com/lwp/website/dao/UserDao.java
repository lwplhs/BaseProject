package com.lwp.website.dao;

import com.lwp.website.entity.Vo.UserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2019/12/20/10:53
 * @Description:
 */
public interface UserDao {

    long countByUsername(String username,String id);


    int deleteByPrimaryKey(String id);

    int insert(UserVo record);


    UserVo selectByPrimaryKey(String uid);

    int updateByPrimaryKey(UserVo record);

    UserVo selectUserByName(String userName);

    /**
     * 可以根据 status 和type进行查询 存放在map里面
     * @param map
     * @return
     */
    List<UserVo> getUserListByStatus(Map<String,Object> map);

    int updatePwd(String id, String pwd);

    int updateUserWithStatus(Map map);

    int updateUserWithDelete(Map map);

    List<UserVo> getListByName(Map map);



}
