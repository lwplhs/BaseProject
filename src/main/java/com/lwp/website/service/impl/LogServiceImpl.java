package com.lwp.website.service.impl;

import com.lwp.website.dao.LoginLogDao;
import com.lwp.website.entity.Vo.LoginLogVo;
import com.lwp.website.service.LogService;
import com.lwp.website.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/28/14:17
 * @Description:
 */
@Service
public class LogServiceImpl implements LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.lwp.website.service.UserService.class);

    @Resource
    private LoginLogDao dao;
    @Override
    public void insertLoginLog(LoginLogVo login_logVo) {
        if(login_logVo != null){
            if(StringUtil.isNull(login_logVo.getLoginTime())) {
                Long date = new Date().getTime();
                login_logVo.setLoginTime(date);
            }
            try {
                dao.insertLog(login_logVo);
            }catch (Exception e){
                LOGGER.info(e.getMessage());
            }

        }
    }
}
