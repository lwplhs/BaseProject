package com.lwp.website.dao;

import com.lwp.website.entity.Vo.LoginLogVo;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/27/16:48
 * @Description:
 */
@Component
public interface LoginLogDao {

    int insertLog(LoginLogVo login_logVo);

    LoginLogVo selectAllLog();

    int selectCountFalse();
}
