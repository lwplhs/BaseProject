package com.lwp.website.service;

import com.lwp.website.entity.Bo.MenuBo;
import com.lwp.website.entity.Vo.MenuVo;
import com.lwp.website.entity.Vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/15/15:41
 * @Description:
 */
public interface MenuService {

    List getMenuList();

    List getSubData(String id);

    int getSort(String series,String id);

    MenuVo getMenuById(String id);

    String saveMenu(MenuVo menuVo, UserVo userVo);

    String getSeriesById(String id);

    Boolean updateMenuWithType(String type,String id,UserVo userVo);

    MenuBo getMenuByUser(UserVo userVo);

    Boolean drag(String dragId,String dropId,UserVo userVo);

    List getRoleTreeData();
}
