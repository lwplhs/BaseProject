package com.lwp.website.service;

import com.lwp.website.entity.Vo.LoginPicVo;
import com.lwp.website.entity.Vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/06/18:02
 * @Description:
 */
@Service
public interface LoginPicService {


    String saveLoginPic(LoginPicVo loginPicVo, UserVo userVo);

    List<LoginPicVo> getListLoginPic();

    String getTotalCount();

    List<LoginPicVo> getListLoginPicByStatus();

    LoginPicVo getLoginPicById(String id);

    boolean updateLoginPic(String ids,String type,UserVo userVo);

}
