package com.tool.box.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;

/**
 * AES 加解密工具类
 *
 * @Author v_haimiyang
 * @Date 2024/4/22 15:18
 * @Version 1.0
 */
public class AesUtils {

    public static final String key = "ocoJUm6Qyw8W8aud";
    public static final String iv = "0102030405060708";

    public static final AES aes = new AES(Mode.CTS, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());

    /**
     * 加密
     *
     * @param content 需要加密的字符串
     * @return 加密结果
     */
    public static String encrypt(String content) {
        return aes.encryptHex(content);
    }

    /**
     * 解密
     *
     * @param content 需要解密的字符串
     * @return 解密结果
     */
    public static String decrypt(String content) {
        return aes.decryptStr(content);
    }

}
