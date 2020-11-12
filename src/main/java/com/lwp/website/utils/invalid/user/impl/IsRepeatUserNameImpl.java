package com.lwp.website.utils.invalid.user.impl;

import com.lwp.website.service.UserService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.invalid.user.IsRepeatUserName;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/11/10:36
 * @Description:
 */
public class IsRepeatUserNameImpl implements ConstraintValidator<IsRepeatUserName,Object> {

    private String vId;

    private String vName;

    private String vPassword;


    @Resource
    private UserService userService;


    @Override
    public void initialize(IsRepeatUserName constraintAnnotation) {
        vId = constraintAnnotation.vId();
        vName = constraintAnnotation.vName();
        vPassword = constraintAnnotation.vPassword();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String message = "";
        if(StringUtil.isNull(o)){
            message = "发生错误，请刷新后重试";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        BeanWrapper beanWrapper = new BeanWrapperImpl(o);
        Object id = beanWrapper.getPropertyValue(vId);
        Object name  = beanWrapper.getPropertyValue(vName);
        Object password = beanWrapper.getPropertyValue(vPassword);
        if(StringUtil.isNull(id) && StringUtil.isNull(password)){
            message = "密码不能为空";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        if(StringUtil.isNull(name)){
            message = "登录名称不能为空";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        long num = userService.getCountByName(String.valueOf(name),StringUtil.isNull(id)?null:String.valueOf(id));
        if(num > 0){
            return false;
        }
        return true;
    }
}
