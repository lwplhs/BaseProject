package com.lwp.website.utils;

import com.lwp.website.entity.Bo.RestResponseBo;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Created with IntelliJ IDEA.
 * 自定义校验工具类
 * @Auther: liweipeng
 * @Date: 2020/06/04/16:26
 * @Description:
 */
@Component
public class InvalidUtil {

    /**
     * 自定义注解校验 统一返回方法
     * @param LOGGER
     * @param bindingResult
     * @return
     */
    public static RestResponseBo error(Logger LOGGER, BindingResult bindingResult){
        for(ObjectError error : bindingResult.getAllErrors()) {
            LOGGER.error(error.getDefaultMessage());
        }
        String msg = (bindingResult.getAllErrors().get(0)).getDefaultMessage();
        return RestResponseBo.fail(-1,msg);
    }

}
