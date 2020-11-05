package com.lwp.website.service;

import com.lwp.website.entity.Vo.LoginLogVo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/28/14:14
 * @Description:
 */
public interface LogService {
    /**
     * 增加登录日志
     * @param login_logVo
     */
    void insertLoginLog(LoginLogVo login_logVo);
}
