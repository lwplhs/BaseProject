package com.lwp.website.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.dao.RoleMenuDao;
import com.lwp.website.entity.Vo.RoleMenuVo;
import com.lwp.website.service.RoleMenuService;
import com.lwp.website.utils.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/17/19:20
 * @Description:
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Resource
    private RoleMenuDao roleMenuDao;


    @Override
    public int save(String data, String roleId) {
        List<RoleMenuVo> rList = new ArrayList();
        /**
         * 删除原数据
         */
        roleMenuDao.deleteByRoleId(roleId);
        try {
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String menuId = jsonObject.getString("id");
                if(!"0".endsWith(menuId)) {
                    RoleMenuVo roleMenuVo = new RoleMenuVo();
                    roleMenuVo.setId(UUID.createID());
                    roleMenuVo.setMenuId(menuId);
                    roleMenuVo.setRoleId(roleId);
                    rList.add(roleMenuVo);
                }
            }

            for (int i = 0; i < rList.size(); i++) {
                RoleMenuVo roleMenuVo = rList.get(i);
                roleMenuDao.insert(roleMenuVo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<RoleMenuVo> getList(String roleId) {

        List<RoleMenuVo> list = roleMenuDao.getListByRoleId(roleId);
        return list;

    }
}
