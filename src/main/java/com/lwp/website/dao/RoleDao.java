package com.lwp.website.dao;

import com.lwp.website.entity.Vo.RoleVo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/16/18:07
 * @Description:
 */
public interface RoleDao {

    List<RoleVo> getListByStatus(Map map);

    int updateWithStatus(Map map);

    int updateWithDelete(Map map);

    int insert(RoleVo roleVo);

    int update(RoleVo roleVo);

    RoleVo getRoleVoById(String id);


}
