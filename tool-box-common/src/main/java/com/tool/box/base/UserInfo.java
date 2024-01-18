package com.tool.box.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录用户信息-用于系统业务使用
 *
 * @Author v_haimiyang
 * @Date 2023/6/26 17:11
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

    /**
     * 账号
     */
    private String account = "sysTem";
    /**
     * 名称
     */
    private String name;
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
}
