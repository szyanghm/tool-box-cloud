package com.tool.box.dto;

import com.tool.box.common.validata.CheckUserName;
import com.tool.box.utils.RegexpUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录-DTO
 *
 * @Author v_haimiyang
 * @Date 2023/6/29 11:23
 * @Version 1.0
 */
@Data
@NotNull(message = "请求参数为空")
public class LoginDTO {

    @NotBlank(message = "用户账号不能为空!")
    @CheckUserName
    private String account;

    @NotBlank(message = "用户密码不能为空!")
    @Pattern(regexp = RegexpUtils.R_PASSWORD, message = "账号或密码错误")
    private String password;

}
