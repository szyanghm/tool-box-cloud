package com.tool.box.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录-DTO
 *
 * @Author v_haimiyang
 * @Date 2023/6/29 11:23
 * @Version 1.0
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户账号不能为空!")
    private String account;

    @NotBlank(message = "用户密码不能为空!")
    private String password;

}
