package com.tool.box.common;

import com.tool.box.enums.TimeUnitEnum;

/**
 * 标准常量
 *
 * @Author v_haimiyang
 * @Date 2023/4/25 15:42
 * @Version 1.0
 */
public class Contents {

    public final static int NUM_0 = 0;
    public final static int NUM_1 = 1;
    public final static int NUM_5 = 5;
    public final static int NUM_2000 = 2000;
    public static final String AUTHORITY = "authority";
    /**
     * token登录key
     */
    public static final String X_ACCESS_TOKEN = "X-Access-Token";
    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    public static String PREFIX_USER_TOKEN = "prefix_user_token:";
    /**
     * 符号：左花括号 }
     */
    public static final String LEFT_CURLY_BRACKET = "{";
    /**
     * 符号：右花括号 }
     */
    public static final String RIGHT_CURLY_BRACKET = "}";
    /**
     * 符号：井号 #
     */
    public static final String WELL_NUMBER = "#";
    /**
     * 符号：/**
     */
    public static final String SLASH_BIT2 = "/**";
    /**
     * 符号：=
     */
    public static final String EQUALS_SIGN = "=";
    /**
     * 符号：;
     */
    public static final String SEMICOLON = ";";
    /**
     * druid数据库监控地址
     */
    public static final String DRUID_SLASH_BIT2 = "/druid/**";
    /**
     * shiro权限放行
     */
    public static final String anon = "anon";
    /**
     * shiro权限拦截
     */
    public static final String authc = "authc";
    /**
     * jwt拦截器
     */
    public static final String JWT_FILTER = "jwtFilter";

}
