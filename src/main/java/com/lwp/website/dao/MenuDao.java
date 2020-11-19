package com.lwp.website.dao;

import com.lwp.website.entity.Vo.MenuVo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/15/15:02
 * @Description:
 */
public interface MenuDao {

    List<MenuVo> getMenuListByNotDelete();

    List<MenuVo> getSubData(String id);

    int getSort(String series,String id);

    MenuVo getMenuById(String id);

    int insertMenu(MenuVo menuVo);

    int updateMenu(MenuVo menuVo);

    int updateMenuWithStatusById(String id);

    int updateMenuStatusById(String id,String status);

    int dragMenuSort(int sort,String id,String type);

    List<MenuVo> getRoleTreeDate();

}
