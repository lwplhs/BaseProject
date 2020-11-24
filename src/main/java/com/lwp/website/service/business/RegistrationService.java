package com.lwp.website.service.business;

import com.alibaba.fastjson.JSONObject;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.entity.Vo.business.registration.ProjectBaseInfoVo;
import com.lwp.website.entity.Vo.business.registration.RegistrationVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/21/23:43
 * @Description:
 */
public interface RegistrationService {

    JSONObject getRegistrationById(String userId);

    List<JSONObject> getProjectBaseInfoByMasterId(String masterId);

    List<JSONObject> getAwardCertificateByMasterId(String masterId);

    List<JSONObject> getIntroductionByMasterId(String masterId);

    String saveRegistration(String jsondata, UserVo userVo);

    void saveProjectBaseInfo(String jsondata,String masterId);

    void saveAwardCertificate(String jsondata,String masterId);

    void savIntroduction(String jsondata,String masterId);

    List<JSONObject> getListReport1(String searchKey);

}
