package com.lwp.website.dao.business.registration;

import com.lwp.website.entity.Vo.business.registration.IntroductionVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/21/21:08
 * @Description:
 */
public interface IntroductionDao {

    int insert(IntroductionVo introductionVo);

    int deleteById(String id);

    int deleteByMasterId(String masterId);

    int update(IntroductionVo introductionVo);

    List<IntroductionVo> getListData(String masterId);

    List<String> getListId(String masterId);



}
