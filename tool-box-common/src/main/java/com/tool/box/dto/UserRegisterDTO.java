package com.tool.box.dto;

import com.tool.box.common.validata.CheckUserName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册用户-DTO
 *
 * @Author v_haimiyang
 * @Date 2024/4/2 10:09
 * @Version 1.0
 */
@Data
public class UserRegisterDTO {

    @Schema(description = "账号")
    @NotBlank(message = "账号不允许为空")
    @CheckUserName
    private String account;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "密码")
    @NotBlank(message = "密码不允许为空")
    private String password;

}
