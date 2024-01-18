package com.tool.box.common.validata;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验固定字符串入参-接口注解
 *
 * @Author v_haimiyang
 * @Date 2023/4/23 16:45
 * @Version 1.0
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValueValidated.class})
public @interface EnumValue {

    /**
     * 是否需要（true:不能为空，false:可以为空）
     */
    boolean isRequire() default false;

    /**
     * 字符串数组
     */
    String[] strValues() default {};

    /**
     * int数组
     */
    int[] intValues() default {};

    /**
     * 枚举类
     */
    Class<?>[] enumClass() default {};

    String message() default "所传参数不在允许的值范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        EnumValue[] value();
    }


}
