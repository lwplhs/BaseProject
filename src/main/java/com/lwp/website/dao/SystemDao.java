package com.lwp.website.dao;

import com.lwp.website.entity.Bo.SystemBo;
import com.lwp.website.entity.Vo.SystemVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/13/17:34
 * @Description:
 */
public interface SystemDao {

    List<SystemVo> getSystem();

    int saveSystem(String key,String value);

}
