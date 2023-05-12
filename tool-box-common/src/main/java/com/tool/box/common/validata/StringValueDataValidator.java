package com.tool.box.common.validata;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义注解
 * 校验固定字符串入参-接口注解实现
 *
 * @Author v_haimiyang
 * @Date 2023/4/23 16:47
 * @Version 1.0
 */
public class StringValueDataValidator implements ConstraintValidator<StringValueData, String> {
    private String[] strValues;

    public void initialize(StringValueData constraintAnnotation) {
        this.strValues = constraintAnnotation.strValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        for (String str : strValues) {
            if (str.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
