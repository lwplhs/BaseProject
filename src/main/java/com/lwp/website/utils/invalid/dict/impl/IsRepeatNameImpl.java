package com.lwp.website.utils.invalid.dict.impl;

import com.lwp.website.service.impl.DictServiceImpl;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.invalid.dict.IsRepeatName;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/10/13/17:54
 * @Description:
 */
public class IsRepeatNameImpl implements ConstraintValidator<IsRepeatName,Object> {

    private String vId;

    private String vName;

    private String vLastId;

    @Override
    public void initialize(IsRepeatName constraintAnnotation) {
        vId = constraintAnnotation.vId();
        vName = constraintAnnotation.vName();
        vLastId = constraintAnnotation.vLastId();

    }

    @Resource
    private DictServiceImpl dictService;

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
        Object lastId = beanWrapper.getPropertyValue(vLastId);
        if(StringUtil.isNull(lastId)){
            message = "发生错误，请刷新后重试";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        if(StringUtil.isNull(name)){
            message = "数据字典名称不能为空";
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        int num = dictService.getCountDictByNameId(String.valueOf(id),String.valueOf(name),String.valueOf(lastId));
        if(num > 0){
            return false;
        }
        return true;
    }
}
