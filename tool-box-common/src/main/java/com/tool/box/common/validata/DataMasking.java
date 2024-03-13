package com.tool.box.common.validata;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tool.box.enums.DataMaskingTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义脱敏注解
 *
 * @Author v_haimiyang
 * @Date 2024/3/13 10:57
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DataMaskingSerialize.class)
public @interface DataMasking {

    /**
     * 数据脱敏类型
     */
    DataMaskingTypeEnum type() default DataMaskingTypeEnum.CUSTOM;

    /**
     * 脱敏开始位置（包含）
     */
    int start() default 0;

    /**
     * 脱敏结束位置（不包含）
     */
    int end() default 0;
}
