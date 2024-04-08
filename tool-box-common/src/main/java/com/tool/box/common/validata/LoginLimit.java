package com.tool.box.common.validata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author v_haimiyang
 * @Date 2024/4/7 16:52
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginLimit {

    int limit() default 5; // 默认登录失败次数限制

}
