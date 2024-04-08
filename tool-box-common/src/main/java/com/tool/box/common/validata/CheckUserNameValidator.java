package com.tool.box.common.validata;

import com.tool.box.utils.RegexpUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义注解校验用户名-实现
 *
 * @Author v_haimiyang
 * @Date 2024/4/8 14:19
 * @Version 1.0
 */
public class CheckUserNameValidator implements ConstraintValidator<CheckUserName, String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.matches(RegexpUtils.R_USERNAME) || str.matches(RegexpUtils.R_PHONE);
    }
}
