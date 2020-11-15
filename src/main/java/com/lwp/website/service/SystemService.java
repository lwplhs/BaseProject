package com.lwp.website.service;

import com.lwp.website.entity.Bo.SystemBo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/13/17:49
 * @Description:
 */
public interface SystemService {

    SystemBo getSystemConfig();

    Map saveSystem(SystemBo systemBo);

}
