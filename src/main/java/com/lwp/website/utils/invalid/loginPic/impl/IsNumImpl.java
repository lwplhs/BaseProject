package com.lwp.website.utils.invalid.loginPic.impl;

import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.Tools;
import com.lwp.website.utils.invalid.loginPic.IsNum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/09/11:07
 * @Description:
 */
public class IsNumImpl implements ConstraintValidator<IsNum, String> {

    @Override
    public void initialize(IsNum constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtil.isNull(s)){
            return true;
        }else {
            if(Tools.isNumber(s)){
                return true;
            }
        }
        return false;
    }

}
