package com.lwp.website.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.dao.RoleDao;
import com.lwp.website.entity.Vo.RoleMenuVo;
import com.lwp.website.entity.Vo.RoleVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.RoleMenuService;
import com.lwp.website.service.RoleService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.TaleUtils;
import com.lwp.website.utils.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/16/18:08
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleMenuService roleMenuService;

    @Override
    public List<RoleVo> getCommonList(String searchKey) {
        Map<String,Object> map = new HashMap();
        List<String> status = new ArrayList();
        //正常启用
        status.add("0");
        //未启用的
        status.add("1");
        map.put("status",status);
        if(StrUtil.isEmpty(searchKey)){
            map.put("searchKey",null);
        }else {
            map.put("searchKey",searchKey);
        }
        List<RoleVo> lists = roleDao.getListByStatus(map);

        return lists;
    }

    @Override
    public Boolean updateByStatus(String ids, String type, UserVo userVo) {
        Boolean bool = false;
        if(type != null){
            switch (type){
                case "1":
                    bool = this.updateStatus(ids,userVo);
                    break;
                case "2":
                    bool = this.updateDelete(ids,userVo);
                    break;
                default:
                    break;
            }
        }
        return bool;
    }

    @Override
    public String save(RoleVo roleVo,String checkedNodes, UserVo userVo) {
        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if(StrUtil.isEmpty(roleVo.getId())){
                String id = UUID.createID();
                roleVo.setId(id);
                num = roleDao.insert(roleVo);
                if(num > 0){
                    roleMenuService.save(checkedNodes,id);
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "添加成功");
                } else {
                    jsonObject.put("code", "100001");
                    jsonObject.put("msg", "添加失败");
                }
            }else {
                num = roleDao.update(roleVo);
                if (num > 0) {
                    roleMenuService.save(checkedNodes,roleVo.getId());
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "修改成功");
                } else {
                    jsonObject.put("code", "100002");
                    jsonObject.put("msg", "修改失败");
                }
            }
        }catch (Exception e){
            jsonObject.put("code","100003");
            jsonObject.put("msg","出现错误，请刷新页面重试");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 根据id 获取 角色内容
     * @param id
     * @return
     */
    @Override
    public RoleVo getRoleById(String id) {
        RoleVo roleVo = roleDao.getRoleVoById(id);
        return roleVo;
    }

    /**
     * 角色编辑的时候 对已存在的功能进行check显示
     * @param list
     * @param roleId
     * @return
     */
    @Override
    public List setCheckValue(List list, String roleId) {
        List<RoleMenuVo> roleMenuVos = roleMenuService.getList(roleId);
        List<String> list1 = new ArrayList();
        List result = new ArrayList();
        for (int i = 0; i < roleMenuVos.size(); i++) {
            list1.add(roleMenuVos.get(i).getMenuId());
        }
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(list.get(i).toString());
            if(list1.contains(jsonObject.get("id"))){
                jsonObject.put("checked",true);
            }else {
                jsonObject.put("checked",false);
            }
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * 更新状态 已启用的修改为未启用 未启用的修改成已启用
     *
     * @param ids
     * @return
     */
    private boolean updateStatus(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            int count = roleDao.updateWithStatus(map);
            if(count > 0){
                return true;
            }
        }

        return false;
    }


    /**
     * 更新删除状态，用户删除
     * @param ids
     * @return
     */
    private boolean updateDelete(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            int count = roleDao.updateWithDelete(map);
            if(count > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 将 ids 转化成list
     * @param ids
     * @return
     */
    private List<String> toList(String ids){
        List<String> list = new ArrayList();
        String[] args = ids.split(",");
        if(null != args && args.length > 0){
            for(int i = 0;i < args.length;i++){
                list.add(args[i]);
            }
        }
        return list;
    }
}
