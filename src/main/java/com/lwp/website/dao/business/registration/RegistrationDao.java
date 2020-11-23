package com.lwp.website.dao.business.registration;

import com.lwp.website.entity.Vo.business.registration.RegistrationVo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/21/21:04
 * @Description:
 */
public interface RegistrationDao {

    int insert(RegistrationVo registrationVo);


    int update(RegistrationVo registrationVo);

    RegistrationVo getDataByUserId(String userId);



}
