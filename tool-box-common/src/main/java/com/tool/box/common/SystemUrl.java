package com.tool.box.common;

import lombok.Data;

/**
 * 系统接口地址
 *
 * @Author v_haimiyang
 * @Date 2024/4/3 14:59
 * @Version 1.0
 */
@Data
public class SystemUrl {

    /**
     * 登录接口地址
     */
    public static final String login_url = "/login";
    /**
     * 注册接口地址
     */
    public static final String register_url = "/register";
    /**
     * 登出接口地址
     */
    public static final String logout_url = "/logout";

    /**
     * 列表查询接口地址
     */
    public static final String findList = "/findList";

    /**
     * 分页列表查询接口地址
     */
    public static final String findPage = "/findPage";
}
