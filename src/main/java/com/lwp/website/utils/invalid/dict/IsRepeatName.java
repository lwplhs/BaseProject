package com.lwp.website.utils.invalid.dict;

import com.lwp.website.utils.invalid.dict.impl.IsRepeatNameImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/10/13/17:52
 * @Description:
 */
@Documented
@Constraint(validatedBy = {IsRepeatNameImpl.class})
//出现的位置 FIELD 属性 METHOD方法 TYPE 类
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
//生命周期 runtime 运行时 class class文件SOURCE 源码
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(com.lwp.website.utils.invalid.dict.IsRepeatName.list.class)
public @interface IsRepeatName {

    String vId() default "0";

    String vLastId() default "0";

    String vName() default "0";

    String message() default "商品类别名称已存在";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface list{
        com.lwp.website.utils.invalid.dict.IsRepeatName[] value();
    }
}
