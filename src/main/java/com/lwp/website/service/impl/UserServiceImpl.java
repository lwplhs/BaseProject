package com.lwp.website.service.impl;

import com.lwp.website.dao.UserVoDao;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.entity.Vo.UserVoExample;
import com.lwp.website.exception.TipException;
import com.lwp.website.service.UserService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.lwp.website.service.UserService.class);

    @Resource
    private UserVoDao userVoDao;

    @Override
    public UserVo queryUserNyUserName(String userName) {
        UserVo userVo = userVoDao.selectUserByName(userName);
        return userVo;
    }

    @Override
    public Integer insertUser(UserVo userVo) {
        int j =  userVoDao.insert(userVo);
        LOGGER.info(String.valueOf(j));
        return null;
    }

    @Override
    public UserVo queryUserById(String uid) {
        return null;
    }

    @Override
    public UserVo login(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new TipException("用户名和密码不能为空");
        }
        UserVoExample example = new UserVoExample();
        UserVoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = userVoDao.countByExample(example);
        if(count < 1){
            throw new TipException("用户名或密码错误");
        }
        String pwd = TaleUtils.MD5encode(username + password);
        criteria.andPasswordEqualTo(pwd);
        List<UserVo> userVos = userVoDao.selectByExample(example);
        if(userVos.size() != 1){
            throw new TipException("用户名或密码错误");
        }
        return userVos.get(0);
    }

    @Override
    public void updateByUid(UserVo userVo) {

    }


    @Override
    public Map changePwd(String oldPwd, String newPwd, String enPwd, UserVo userVo) {
        Map map = new HashMap();
        validPwd(oldPwd,newPwd,enPwd,userVo,map);
        if(!"6".equals(String.valueOf(map.get("code")))){
            map.put("code","-1");
            return map;
        }
        //修改密码
        String username = userVo.getUsername();
        String newPassword = TaleUtils.MD5encode(username+newPwd);
        int n = userVoDao.updatePwd(userVo.getId(),newPassword);
        if(n > 0){
            map.put("code","1");
            map.put("msg","修改密码成功，请重新登录");
        }else {
            map.put("code","-1");
            map.put("msg","修改密码失败，请刷新页面重试");
        }
        return map;
    }

    private Map validPwd(String oldPwd,String newPwd,String enPwd,UserVo userVo , Map map){
        if(StringUtil.isNull(userVo)){
            map.put("code","7");
            map.put("msg","用户未登录，请登录后重试");
            return map;
        }
        if(StringUtil.isNull(oldPwd)){
            map.put("code","1");
            map.put("msg","原密码不能为空");
            return map;
        }
        if(StringUtil.isNull(newPwd)){
            map.put("code","2");
            map.put("msg","新密码不能为空");
            return map;
        }
        if(StringUtil.isNull(enPwd)){
            map.put("code","3");
            map.put("msg","确认密码不能为空");
            return map;
        }
        if(!newPwd.equals(enPwd)){
            map.put("code","4");
            map.put("msg","新密码与确认密码不匹配，请重新确认");
            return map;
        }
        //校验原密码
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if(!password.equals(TaleUtils.MD5encode(username+oldPwd))){
            map.put("code","5");
            map.put("msg","原密码不正确，请重新输入");
            return map;
        }
        map.put("code","6");
        return map;
    }
}
