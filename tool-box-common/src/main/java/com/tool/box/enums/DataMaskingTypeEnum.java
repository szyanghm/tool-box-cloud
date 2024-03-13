package com.tool.box.enums;

/**
 * 数据脱敏枚举
 *
 * @Author v_haimiyang
 * @Date 2024/3/13 11:11
 * @Version 1.0
 */
public enum DataMaskingTypeEnum {

    /**
     * 用户ID
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 邮箱
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌号
     */
    CAR_LICENSE,
    /**
     * 银行卡号
     */
    BANK_CARD,
    /**
     * IPv4地址
     */
    IPV4,
    /**
     * IPv6地址
     */
    IPV6,
    /**
     * 自定义类型
     */
    CUSTOM;

}
