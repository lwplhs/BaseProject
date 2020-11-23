package com.lwp.website.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwp.website.dao.AttachmentDao;
import com.lwp.website.dao.business.registration.AwardCertificateDao;
import com.lwp.website.dao.business.registration.IntroductionDao;
import com.lwp.website.dao.business.registration.ProjectBaseInfoDao;
import com.lwp.website.dao.business.registration.RegistrationDao;
import com.lwp.website.entity.Vo.AttachmentVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.entity.Vo.business.registration.AwardCertificateVo;
import com.lwp.website.entity.Vo.business.registration.IntroductionVo;
import com.lwp.website.entity.Vo.business.registration.ProjectBaseInfoVo;
import com.lwp.website.entity.Vo.business.registration.RegistrationVo;
import com.lwp.website.service.business.RegistrationService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/21/23:44
 * @Description:
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Resource
    private RegistrationDao registrationDao;

    @Resource
    private AwardCertificateDao awardCertificateDao;

    @Resource
    private IntroductionDao introductionDao;

    @Resource
    private ProjectBaseInfoDao projectBaseInfoDao;

    @Resource
    private AttachmentDao attachmentDao;


    /**
     * 根据用户id获取信息
     * @param userId
     * @return
     */
    @Override
    public JSONObject getRegistrationById(String userId) {

        RegistrationVo registrationVo = registrationDao.getDataByUserId(userId);
        if(registrationVo == null){
            registrationVo = new RegistrationVo();
        }
        JSONObject reg = new JSONObject();
        reg.put("id", StrUtil.isEmpty(registrationVo.getId())?"":registrationVo.getId());
        reg.put("name", StrUtil.isEmpty(registrationVo.getName())?"":registrationVo.getName());
        reg.put("sex", StrUtil.isEmpty(registrationVo.getSex())?"":registrationVo.getSex());
        reg.put("age", StrUtil.isEmpty(registrationVo.getAge())?"":registrationVo.getAge());
        reg.put("identity", StrUtil.isEmpty(registrationVo.getIdentity())?"":registrationVo.getIdentity());
        reg.put("unit", StrUtil.isEmpty(registrationVo.getUnit())?"":registrationVo.getUnit());
        reg.put("department", StrUtil.isEmpty(registrationVo.getDepartment())?"":registrationVo.getDepartment());
        reg.put("office", StrUtil.isEmpty(registrationVo.getOffice())?"":registrationVo.getOffice());
        reg.put("telephone", StrUtil.isEmpty(registrationVo.getTelephone())?"":registrationVo.getTelephone());
        return reg;
    }

    @Override
    public List<JSONObject> getProjectBaseInfoByMasterId(String masterId) {

        if(StrUtil.isEmpty(masterId)){
            return null;
        }
        List<ProjectBaseInfoVo> list = projectBaseInfoDao.getListData(masterId);

        if(list == null ||list.size() <= 0){
            return null;
        }
        List<JSONObject> result = new ArrayList();
        //转化成json
        for (int i = 0; i < list.size(); i++) {
            ProjectBaseInfoVo projectBaseInfoVo = list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",StrUtil.isEmpty(projectBaseInfoVo.getId())?"":projectBaseInfoVo.getId());
            jsonObject.put("no",StrUtil.isEmpty(projectBaseInfoVo.getNo())?"":projectBaseInfoVo.getNo());
            jsonObject.put("name",StrUtil.isEmpty(projectBaseInfoVo.getName())?"":projectBaseInfoVo.getName());
            jsonObject.put("patentNo",StrUtil.isEmpty(projectBaseInfoVo.getPatentNo())?"":projectBaseInfoVo.getPatentNo());
            jsonObject.put("inventor",StrUtil.isEmpty(projectBaseInfoVo.getInventor())?"":projectBaseInfoVo.getInventor());

            String stage = projectBaseInfoVo.getStage();
            String[] stages = new String[]{"0","0","0","0"};
            if(StringUtil.isNotNull(stage)){
                //分成数组
                String[] strs = stage.split(",");
                for (int j = 0; j < strs.length; j++) {
                    int temp = Integer.parseInt(strs[j]);
                    if(temp >=0 && temp <4){
                        stages[temp]="1";
                    }
                }
            }
            for (int j = 0; j < stages.length; j++) {
                jsonObject.put("stage"+(j+1),stages[j]);
            }
            String introduction = projectBaseInfoVo.getIntroduction();
            String[] introductions = new String[]{"0","0","0","0"};

            if(StringUtil.isNotNull(introduction)){
                //
                String[] strs = introduction.split(",");
                for (int j = 0; j < strs.length; j++) {
                    int temp = Integer.parseInt(strs[j]);
                    if(temp >=0 && temp <4){
                        introductions[temp]="1";
                    }
                }
            }
            for (int j = 0; j < introductions.length; j++) {
                jsonObject.put("introduction"+(j+1),introductions[j]);
            }
            result.add(jsonObject);
        }


        return result;
    }

    /**
     * 获取获奖信息
     * @param masterId
     * @return
     */
    @Override
    public List<JSONObject> getAwardCertificateByMasterId(String masterId) {

        if(StrUtil.isEmpty(masterId)){
            return null;
        }
        List<AwardCertificateVo> list = awardCertificateDao.getListData(masterId);
        if(null == list || list.size() <= 0){
            return null;
        }
        List<JSONObject> result = new ArrayList();
        //转化成json
        for (int i = 0; i < list.size(); i++) {
            AwardCertificateVo awardCertificateVo = list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",StrUtil.isEmpty(awardCertificateVo.getId())?"":awardCertificateVo.getId());
            jsonObject.put("no",StrUtil.isEmpty(awardCertificateVo.getNo())?"":awardCertificateVo.getNo());
            jsonObject.put("name",StrUtil.isEmpty(awardCertificateVo.getName())?"":awardCertificateVo.getName());
            String attachmentId = awardCertificateVo.getAttachmentId();
            if(StrUtil.isEmpty(attachmentId)){
                jsonObject.put("aId","");
                jsonObject.put("aName","");
            }
            else {
                AttachmentVo attachmentVo = attachmentDao.getAttachmentById(attachmentId);
                if(attachmentVo!=null){
                    jsonObject.put("aId",attachmentId);
                    jsonObject.put("aName",attachmentVo.getOldName());
                }else {
                    jsonObject.put("aId","");
                    jsonObject.put("aName","");
                }
            }
            result.add(jsonObject);

        }


        return result;
    }

    @Override
    public List<JSONObject> getIntroductionByMasterId(String masterId) {
        if(StrUtil.isEmpty(masterId)){
            return null;
        }
        List<IntroductionVo> list = introductionDao.getListData(masterId);
        if(null == list || list.size() <= 0){
            return null;
        }
        List<JSONObject> result = new ArrayList();
        //转化成json
        for (int i = 0; i < list.size(); i++) {
            IntroductionVo introductionVo = list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",StrUtil.isEmpty(introductionVo.getId())?"":introductionVo.getId());
            jsonObject.put("no",StrUtil.isEmpty(introductionVo.getNo())?"":introductionVo.getNo());
            jsonObject.put("name",StrUtil.isEmpty(introductionVo.getName())?"":introductionVo.getName());
            String attachmentId = introductionVo.getAttachmentId();
            if(StrUtil.isEmpty(attachmentId)){
                jsonObject.put("aId","");
                jsonObject.put("aName","");
            }
            else {
                AttachmentVo attachmentVo = attachmentDao.getAttachmentById(attachmentId);
                if(attachmentVo!=null){
                    jsonObject.put("aId",attachmentId);
                    jsonObject.put("aName",attachmentVo.getOldName());
                }else {
                    jsonObject.put("aId","");
                    jsonObject.put("aName","");
                }
            }
            result.add(jsonObject);

        }


        return result;
    }

    @Override
    public String saveRegistration(String jsondata, UserVo userVo) {

        JSONObject jsonObject = JSONObject.parseObject(jsondata);
        RegistrationVo registrationVo = new RegistrationVo();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String sex = jsonObject.getString("sex");
        String age = jsonObject.getString("age");
        String identity = jsonObject.getString("identity");
        String unit = jsonObject.getString("unit");
        String department = jsonObject.getString("department");
        String office = jsonObject.getString("office");
        String telephone = jsonObject.getString("telephone");
        registrationVo.setName(name);
        registrationVo.setSex(sex);
        registrationVo.setAge(age);
        registrationVo.setIdentity(identity);
        registrationVo.setUnit(unit);
        registrationVo.setDepartment(department);
        registrationVo.setOffice(office);
        registrationVo.setTelephone(telephone);
        if(StrUtil.isEmpty(id)){
            id = UUID.createID();
            registrationVo.setId(id);
            registrationVo.setUserId(userVo.getId());
            registrationVo.setCreateTime(new Date());
            int num = registrationDao.insert(registrationVo);
        }
        else {
            registrationVo.setId(id);
            registrationVo.setUpdateTime(new Date());
            int num = registrationDao.update(registrationVo);
        }
        return registrationVo.getId();
    }

    @Override
    public void saveProjectBaseInfo(String jsondata,String masterId) {

        JSONArray jsonArray = JSONArray.parseArray(jsondata);
        //删除子表数据
        projectBaseInfoDao.deleteByMasterId(masterId);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = UUID.createID();
            String no = jsonObject.getString("no");
            String name = jsonObject.getString("name");
            String patentNo = jsonObject.getString("patentNo");
            String inventor = jsonObject.getString("inventor");
            Boolean stage1 = jsonObject.getBoolean("stage1");
            Boolean stage2 = jsonObject.getBoolean("stage2");
            Boolean stage3 = jsonObject.getBoolean("stage3");
            Boolean stage4 = jsonObject.getBoolean("stage4");
            Boolean introduction1 = jsonObject.getBoolean("introduction1");
            Boolean introduction2 = jsonObject.getBoolean("introduction2");
            Boolean introduction3 = jsonObject.getBoolean("introduction3");
            Boolean introduction4 = jsonObject.getBoolean("introduction4");
            String stage = "";
            if(stage1){
                stage = stage + "0,";
            }if(stage2){
                stage = stage + "1,";
            }if(stage3){
                stage = stage + "2,";
            }if(stage4){
                stage = stage + "3,";
            }if(StrUtil.isNotEmpty(stage)){
                if(stage.endsWith(",")){
                    stage = stage.substring(0,stage.length()-1);
                }
            }
            String introduction = "";
            if(introduction1){
                introduction = introduction + "0,";
            }if(introduction2){
                introduction = introduction + "1,";
            }if(introduction3){
                introduction = introduction + "2,";
            }if(introduction4){
                introduction = introduction + "3,";
            }if(StrUtil.isNotEmpty(introduction)){
                if(introduction.endsWith(",")){
                    introduction = introduction.substring(0,introduction.length()-1);
                }
            }
            ProjectBaseInfoVo projectBaseInfoVo = new ProjectBaseInfoVo();
            projectBaseInfoVo.setId(id);
            projectBaseInfoVo.setMasterId(masterId);
            projectBaseInfoVo.setNo(no);
            projectBaseInfoVo.setName(name);
            projectBaseInfoVo.setPatentNo(patentNo);
            projectBaseInfoVo.setInventor(inventor);
            projectBaseInfoVo.setStage(stage);
            projectBaseInfoVo.setIntroduction(introduction);
            projectBaseInfoDao.insert(projectBaseInfoVo);


        }




    }

    @Override
    public void saveAwardCertificate(String jsondata,String masterId) {
        JSONArray jsonArray = JSONArray.parseArray(jsondata);
        //删除子表数据
        awardCertificateDao.deleteByMasterId(masterId);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = UUID.createID();
            String no = jsonObject.getString("no");
            String name = jsonObject.getString("name");
            String aId = jsonObject.getString("aId");

            AwardCertificateVo awardCertificateVo =new AwardCertificateVo();
            awardCertificateVo.setId(id);
            awardCertificateVo.setAttachmentId(aId);
            awardCertificateVo.setMasterId(masterId);
            awardCertificateVo.setName(name);
            awardCertificateVo.setNo(no);
            awardCertificateDao.insert(awardCertificateVo);
        }
    }

    @Override
    public void savIntroduction(String jsondata,String masterId) {
        JSONArray jsonArray = JSONArray.parseArray(jsondata);
        //删除子表数据
        introductionDao.deleteByMasterId(masterId);

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = UUID.createID();
            String no = jsonObject.getString("no");
            String name = jsonObject.getString("name");
            String aId = jsonObject.getString("aId");

            IntroductionVo introductionVo =new IntroductionVo();
            introductionVo.setId(id);
            introductionVo.setAttachmentId(aId);
            introductionVo.setMasterId(masterId);
            introductionVo.setName(name);
            introductionVo.setNo(no);
            introductionDao.insert(introductionVo);
        }
    }
}
