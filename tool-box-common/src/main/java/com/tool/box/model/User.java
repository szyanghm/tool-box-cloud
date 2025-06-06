package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @author v_haimiyang
 * @since 2023-04-20
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_user")
public class User extends BaseModel<User> {

    @Schema(description = "账号")
    private String account;
    @Schema(description = "手机号码")
    private String phone;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "角色")
    private String roleCode;
    @Schema(description = "密码加盐")
    private String salt;
    @Schema(description = "0未锁定/1锁定")
    private int status;

}
