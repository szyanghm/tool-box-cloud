package com.tool.box.common.validata;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义注解校验用户名
 *
 * @Author v_haimiyang
 * @Date 2024/4/8 14:20
 * @Version 1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {CheckUserNameValidator.class})
public @interface CheckUserName {

    String message() default "账号或密码错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
