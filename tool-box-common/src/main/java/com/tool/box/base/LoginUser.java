package com.tool.box.base;

import lombok.Data;

import java.util.List;

/**
 * 登录用户信息-用于登录校验
 *
 * @Author v_haimiyang
 * @Date 2023/7/28 16:42
 * @Version 1.0
 */
@Data
public class LoginUser {

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
    /**
     * 0未锁定/1锁定
     */
    private int status;
    /**
     * 权限
     */
    List<String> permissions;
}
