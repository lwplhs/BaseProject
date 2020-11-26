package com.lwp.website.dao.business.registration;

import com.lwp.website.entity.Vo.business.registration.ProjectBaseInfoVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/21/21:11
 * @Description:
 */
public interface ProjectBaseInfoDao  {


    int insert(ProjectBaseInfoVo projectBaseInfoVo);

    int update(ProjectBaseInfoVo projectBaseInfoVo);

    int deleteById(String id);

    int deleteByMasterId(String masterId);

    List<ProjectBaseInfoVo> getListData(String masterId);

    List<String> getListId(String masterId);

    List<ProjectBaseInfoVo> getListByArea(String area);

}
