package com.tool.box.base;

import lombok.Data;

/**
 * 登录用户信息
 *
 * @Author v_haimiyang
 * @Date 2023/6/26 17:11
 * @Version 1.0
 */
@Data
public class UserInfo {

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
