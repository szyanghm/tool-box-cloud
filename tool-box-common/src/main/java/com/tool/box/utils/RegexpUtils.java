package com.tool.box.utils;

/**
 * 正则工具类
 *
 * @Author v_haimiyang
 * @Date 2024/4/8 15:59
 * @Version 1.0
 */
public class RegexpUtils {

    /**
     * 密码校验-包含大小写数字6-20位
     */
    public static final String R_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{5,19}$";
    /**
     * 用户名校验-字母开头6-20位
     */
    public static final String R_USERNAME = "^[a-zA-Z][a-zA-Z0-9]{4,19}$";
    /**
     * 手机号校验
     */
    public static final String R_PHONE = "^1[3-9]\\d{9}$";

}
