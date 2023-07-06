package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
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

    /**
     * 账号
     */
    private String account;
    /**
     * 名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色
     */
    private String role;
    /**
     * 密码加盐
     */
    private String salt;

}
