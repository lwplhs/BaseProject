package com.lwp.website.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lwp.website.dao.UserRoleDao;
import com.lwp.website.entity.Vo.UserRoleVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.UserRoleService;
import com.lwp.website.utils.UUID;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/18/20:34
 * @Description:
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {


    @Resource
    private UserRoleDao userRoleDao;

    /**
     * 添加用户角色 对照表信息
     * @param userVo
     */
    @Override
    public void insertData(UserVo userVo) {
        String userId = userVo.getId();

        //删除原数据
        userRoleDao.deleteByUserId(userId);

        String roles = userVo.getGroupName();
        if(StrUtil.isEmpty(roles)){
            return;
        }

        String[] roleStrs = roles.split(",");
        if(null != roleStrs && roleStrs.length > 0){
            for (int i = 0; i < roleStrs.length; i++) {
                String roleId = roleStrs[i];
                UserRoleVo userRoleVo = new UserRoleVo();
                userRoleVo.setId(UUID.createID());
                userRoleVo.setUserId(userId);
                userRoleVo.setRoleId(roleId);
                userRoleDao.insert(userRoleVo);
            }
        }
    }

    @Override
    public List<String> getRoleIdByUserId(String userId) {

        List<UserRoleVo> userRoleVoList = userRoleDao.getListByUserId(userId);
        List<String> list = new ArrayList();
        if(null != userRoleVoList && userRoleVoList.size() > 0){
            for (int i = 0; i < userRoleVoList.size(); i++) {
                String roleId = userRoleVoList.get(i).getRoleId();
                list.add(roleId);
            }
        }

        return list;
    }
}
