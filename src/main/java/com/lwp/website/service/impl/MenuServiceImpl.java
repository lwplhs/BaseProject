package com.lwp.website.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.dao.MenuDao;
import com.lwp.website.entity.Bo.MenuBo;
import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.entity.Vo.MenuVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.MenuService;
import com.lwp.website.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/15/15:46
 * @Description:
 */
@Service
public class MenuServiceImpl implements MenuService {

    private Logger LOGGER = LoggerFactory.getLogger(DictServiceImpl.class);

    @Resource
    private MenuDao menuDao;

    @Override
    public List getMenuList() {
        List<MenuVo> menuVoList = menuDao.getMenuListByNotDelete();
        List temp = new ArrayList();
        for (int i = 0; i < menuVoList.size(); i++) {
            MenuVo menuVo = menuVoList.get(i);
            JSONObject jsonObject = new JSONObject();
            String id = menuVo.getId();
            jsonObject.put("id",id);
            String pid = menuVo.getPid();
            jsonObject.put("pId",pid);
            String name = menuVo.getName();
            jsonObject.put("name",name);
            String status = menuVo.getStatus();
            jsonObject.put("status",status);
            String series = menuVo.getSeries();
            jsonObject.put("series",series);
            jsonObject.put("drag",false);
            jsonObject.put("drop",false);
            temp.add(jsonObject);
        }
        return temp;
    }

    @Override
    public List getSubData(String id) {
        List<MenuVo> list = menuDao.getSubData(id);
/*        List temp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            MenuVo menuVo = list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",menuVo.getId());
            jsonObject.put("name",menuVo.getName());
            jsonObject.put("url",menuVo.getUrl());
            jsonObject.put("status",menuVo.getStatus());
            jsonObject.put("sort",menuVo.getSort());
            temp.add(jsonObject);
        }*/
        return list;
    }

    @Override
    public String getSort(String series,String id) {
        String sort = menuDao.getSort(series,id);
        return sort;
    }

    @Override
    public MenuVo getMenuById(String id) {
        MenuVo menuVo = menuDao.getMenuById(id);
        return menuVo;
    }

    @Override
    @Transactional
    public String saveMenu(MenuVo menuVo, UserVo userVo) {
        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if(StrUtil.isEmpty(menuVo.getId())){
                String sort = getSort(menuVo.getSeries(),menuVo.getPid());
                menuVo.setSort(sort);
                String id = UUID.createID();
                menuVo.setId(id);
                //新增
                num = menuDao.insertMenu(menuVo);
                if(num > 0){
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "添加成功");
                } else {
                    jsonObject.put("code", "100001");
                    jsonObject.put("msg", "添加失败");
                }
            }else {
                //更新
                num = menuDao.updateMenu(menuVo);
                if (num > 0) {
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
     * 根据id 获取series
     * @param id
     * @return
     */
    @Override
    public String getSeriesById(String id) {
        MenuVo menuVo = menuDao.getMenuById(id);
        String series = "";
        if(null != menuVo){
            series = menuVo.getSeries();
        }
        return series;
    }

    @Override
    public Boolean updateMenuWithType(String type, String id, UserVo userVo) {
        switch (type){
            case "1":
                return updateStatus(id,userVo);
            case "2":
                return updateDelete(id,userVo);
        }
        return false;
    }

    /**
     * 根据用户权限获取菜单
     * @param userVo
     * @return
     */
    @Override
    public MenuBo getMenuByUser(UserVo userVo) {

        MenuBo rootMenuBo = new MenuBo();
        MenuVo root = getMenuById("0");
        rootMenuBo.setId("0");
        rootMenuBo.setName(root.getName());
        rootMenuBo.setIcon(root.getIcon());
        rootMenuBo.setUrl(root.getUrl());
        List<MenuBo> subMenuList = getMenu("0");
        rootMenuBo.setSubMenuList(subMenuList);

        return rootMenuBo;
    }

    private List<MenuBo> getMenu(String id){
        List<MenuBo> subMenuList = new ArrayList();
        List<MenuVo> list = getSubData(id);
        if(null == list || list.size() <= 0){
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            MenuVo menuVo = list.get(i);
            MenuBo menuBo = new MenuBo();
            menuBo.setId(menuVo.getId());
            menuBo.setName(menuVo.getName());
            menuBo.setUrl(menuVo.getUrl());
            menuBo.setIcon(menuVo.getIcon());
            List<MenuBo> temp = getMenu(menuVo.getId());
            menuBo.setSubMenuList(temp);
            subMenuList.add(menuBo);
        }
        return subMenuList;

    }


    /**
     * 修改状态 启用/未启用
     * @param id
     * @param userVo
     * @return
     */
    private Boolean updateStatus(String id,UserVo userVo){

        //修改 id的状态
        int num = menuDao.updateMenuWithStatusById(id);
        if(num > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean updateDelete(String id,UserVo userVo){
        //删除 id
        int num = menuDao.updateMenuStatusById(id,"2");
        if(num > 0){
            return true;
        }
        return false;
    }
}
