package com.tool.box.base;

import lombok.Data;

/**
 * 登录用户信息-用于给前端展示
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
     * 角色
     */
    private String roleCode;
    /**
     * 0未锁定/1锁定
     */
    private int status;
}
