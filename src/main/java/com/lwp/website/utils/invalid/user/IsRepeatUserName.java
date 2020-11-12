package com.lwp.website.utils.invalid.user;


import com.lwp.website.utils.invalid.user.impl.IsRepeatUserNameImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/11/11/10:35
 * @Description:
 */
@Documented
@Constraint(validatedBy = {IsRepeatUserNameImpl.class})
//出现的位置 FIELD 属性 METHOD方法 TYPE 类
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
//生命周期 runtime 运行时 class class文件SOURCE 源码
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(com.lwp.website.utils.invalid.user.IsRepeatUserName.list.class)
public @interface IsRepeatUserName {
    String vId() default "0";

    String vName() default "0";

    String vPassword();

    String message() default "商品类别名称已存在";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface list{
        com.lwp.website.utils.invalid.user.IsRepeatUserName[] value();
    }
}
