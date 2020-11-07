package com.lwp.website.dao;

import com.lwp.website.entity.Vo.LoginPicVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/06/18:12
 * @Description:
 */
@Component
public interface LoginPicDao {
    /**
     * 插入登录背景图
     * @param loginPicVo
     */
    int insertLoginPic(LoginPicVo loginPicVo);

    int updateLoginPic(LoginPicVo loginPicVo);

    /**
     * 获取未删除的登录背景图
     * @return
     */
    List<LoginPicVo> getListLoginPic();

    /**
     * 获取未删除的登录背景图的总数
     * @return
     */
    String getTotalCount();

    /**
     * 获取启用的登录背景图
     * @return
     */
    List<LoginPicVo> getListLoginPicByStatus();

    /**
     * 获取最大排序值
     * @return
     */
    int getMaxSort();

    /**
     * 根据id获取 登录背景图
     * @param id
     * @return
     */
    LoginPicVo getLoginPicById(String id);

    /**
     * 更新登录背景图启用/未启用
     * @param map
     * @return
     */
    int updateLoginPicWithStatus(Map<String,Object> map);

    /**
     * 更新登录背景图是否删除
     * @param map
     * @return
     */
    int updateLoginPicWithDelete(Map<String,Object> map);
}
