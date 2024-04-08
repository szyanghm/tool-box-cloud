package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("手机号码")
    private String phone;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("角色")
    private String roleCode;
    @ApiModelProperty("密码加盐")
    private String salt;
    @ApiModelProperty("0未锁定/1锁定")
    private int status;

}
