package com.lwp.website.service;

import com.lwp.website.entity.Vo.RoleVo;
import com.lwp.website.entity.Vo.UserVo;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/16/18:08
 * @Description:
 */
public interface RoleService {

    List<RoleVo> getCommonList(String searchKey);

    Boolean updateByStatus(String ids,String type, UserVo userVo);

    String save(RoleVo roleVo,String checkedNodes,UserVo userVo);

    RoleVo getRoleById(String id);

    List setCheckValue(List list,String roleId);

}
