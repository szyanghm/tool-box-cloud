package com.tool.box.common;

/**
 * 标准常量
 *
 * @Author v_haimiyang
 * @Date 2023/4/25 15:42
 * @Version 1.0
 */
public class Contents {

    /**
     * 常量0
     */
    public final static int NUM_0 = 0;

    public static final String AUTHORITY = "authority";

    public static final String TOKEN = "token";

    /** 登录用户Shiro权限缓存KEY前缀 */
    public static String PREFIX_USER_SHIRO_CACHE  = "shiro:cache:org.tool.box.shiro.ShiroRealm.authorizationCache:";
    /** 登录用户Token令牌缓存KEY前缀 */
    public static String PREFIX_USER_TOKEN  = "prefix_user_token:";

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

}
