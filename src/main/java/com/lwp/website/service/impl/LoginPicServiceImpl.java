package com.lwp.website.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.dao.LoginPicDao;
import com.lwp.website.entity.Vo.LoginPicVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.LoginPicService;
import com.lwp.website.service.WebUploadService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/06/18:02
 * @Description:
 */
@Service(value = "loginPicService")
public class LoginPicServiceImpl implements LoginPicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPicServiceImpl.class);

    @Resource
    private LoginPicDao loginPicDao;

    @Resource
    private WebUploadService webUploadService;
    /**
     * 添加首页登录背景图
     * @param loginPicVo
     * @param userVo
     */
    @Override
    public String saveLoginPic(LoginPicVo loginPicVo, UserVo userVo) {
        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if (StringUtil.isNull(loginPicVo.getId())) {
                String id = UUID.createID();
                loginPicVo.setId(id);
                loginPicVo.setCreateTime(new Date());
                loginPicVo.setUpdateTime(new Date());
                loginPicVo.setCreateUserId(userVo.getId());
                loginPicVo.setUpdateUserId(userVo.getId());
                if (StringUtil.isNull(loginPicVo.getSort())) {
                    String sort = String.valueOf(loginPicDao.getMaxSort());
                    loginPicVo.setSort(sort);
                }
                if(StrUtil.isEmpty(loginPicVo.getStatus())){
                    loginPicVo.setStatus("1");
                }
                num = loginPicDao.insertLoginPic(loginPicVo);
                if(num > 0){
                    jsonObject.put("code",1);
                    jsonObject.put("msg","添加成功");
                }else {
                    jsonObject.put("code",100001);
                    jsonObject.put("msg","添加失败");
                }

            } else {
                loginPicVo.setUpdateTime(new Date());
                loginPicVo.setUpdateUserId(userVo.getId().toString());
                if (StringUtil.isNull(loginPicVo.getSort())) {
                    String sort = String.valueOf(loginPicDao.getMaxSort());
                    loginPicVo.setSort(sort);
                }
                num = loginPicDao.updateLoginPic(loginPicVo);
                if(num > 0){
                    jsonObject.put("code",1);
                    jsonObject.put("msg","修改成功");
                }else {
                    jsonObject.put("code",100002);
                    jsonObject.put("msg","修改失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("code",100003);
            jsonObject.put("msg","出现错误，请刷新页面重试");
        }
        return jsonObject.toString();
    }

    /**
     * 获取登录背景图 未删除
     * @return
     */
    @Override
    public List<LoginPicVo> getListLoginPic() {
        List<LoginPicVo> list = loginPicDao.getListLoginPic();
        for(int i = 0;i < list.size();i++){
            LoginPicVo loginPicVo = list.get(i);
            String path = loginPicVo.getPath();
            if(StrUtil.isEmpty(path)){
                String temp = webUploadService.getPathById("");
                list.get(i).setPath(temp);
            }
        }
        return list;
    }
    /**
     * 获取首页登录背景图 未删除
     * @return
     */
    @Override
    public String getTotalCount() {
        String count = loginPicDao.getTotalCount();

        return count;
    }

    /**
     * 获取首页轮播 -已启用未删除
     * @return
     */
    @Override
    public List<LoginPicVo> getListLoginPicByStatus() {
        List<LoginPicVo> list = loginPicDao.getListLoginPicByStatus();
        for(int i = 0;i < list.size();i++){
            LoginPicVo carouselVo = list.get(i);
            String path = carouselVo.getPath();
            if(StrUtil.isEmpty(path)){
                String temp = webUploadService.getPathById("");
                list.get(i).setPath(temp);
            }
        }
        return list;
    }
    /**
     * 根据id获取首页登录背景图
     * @param id
     * @return
     */
    @Override
    public LoginPicVo getLoginPicById(String id) {
        LoginPicVo loginPicVo = new LoginPicVo();
        if(StrUtil.isNotEmpty(id)){
            loginPicVo = loginPicDao.getLoginPicById(id);
            String path = loginPicVo.getPath();
            if(StrUtil.isEmpty(path)){
                String temp = webUploadService.getPathById("");
                loginPicVo.setPath(temp);
            }
        }
        return loginPicVo;
    }

    /**
     * 根据类型 更新首页登录背景图
     * @param ids ids
     * @param type 1、启用/未启用 2 删除
     * @return
     */
    @Override
    public boolean updateLoginPic(String ids, String type, UserVo userVo) {
        Boolean bool = false;
        if(type != null){
            switch (type){
                case "1":
                    bool = this.updateStatus(ids,userVo);
                    break;
                case "2":
                    bool = this.updateDelete(ids,userVo);
                    break;
                default:
                    break;
            }
        }
        return bool;
    }
    /**
     * 更新状态 已启用的修改为未启用 未启用的修改成已启用
     *
     * @param ids
     * @return
     */
    private boolean updateStatus(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            map.put("updateTime",new Date());
            map.put("updateUserId",userVo.getId());
            int count = loginPicDao.updateLoginPicWithStatus(map);
            if(count > 0){
                return true;
            }
        }

        return false;
    }

    /**
     * 更新删除状态，将首页登录背景图删除
     * @param ids
     * @return
     */
    private boolean updateDelete(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            map.put("updateTime",new Date());
            map.put("updateUserId",userVo.getId());
            int count = loginPicDao.updateLoginPicWithDelete(map);
            if(count > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 将 ids 转化成list
     * @param ids
     * @return
     */
    private List<String> toList(String ids){
        List<String> list = new ArrayList();
        String[] args = ids.split(",");
        if(null != args && args.length > 0){
            for(int i = 0;i < args.length;i++){
                list.add(args[i]);
            }
        }
        return list;
    }
}
