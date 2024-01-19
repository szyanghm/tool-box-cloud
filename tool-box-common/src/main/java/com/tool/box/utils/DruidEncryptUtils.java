package com.tool.box.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Druid加密工具
 *
 * @Author v_haimiyang
 * @Date 2023/4/19 15:43
 * @Version 1.0
 */
@Slf4j
public class DruidEncryptUtils {

    /**
     * 私钥
     */
    private static final String privateKey;

    /**
     * 公钥
     */
    private static final String publicKey;

    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            privateKey = keyPair[0];
            log.info("privateKey-->{}", privateKey);
            publicKey = keyPair[1];
            log.info("publicKey-->{}", publicKey);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error(e.getMessage());
            throw new InternalApiException(SystemCodeEnum.PASSWORD_ENCRYPT_FAILED);
        }
    }

    /**
     * 明文加密
     *
     * @param plaintext 明文字符串
     * @return 加密后
     */
    @SneakyThrows
    public static String encode(String plaintext) {
        log.info("明文字符串：{}", plaintext);
        String ciphertext = ConfigTools.encrypt(privateKey, plaintext);
        log.info("加密后字符串：{}", ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     *
     * @param ciphertext 加密字符串
     * @return 解码结果
     */
    @SneakyThrows
    public static String decode(String ciphertext) {
        log.info("加密字符串：{}", ciphertext);
        String plaintext = ConfigTools.decrypt(publicKey, ciphertext);
        log.info("解密后的字符串：{}", plaintext);
        return plaintext;
    }

    public static void main(String[] args) {
        String a = "123456";
        String encode = DruidEncryptUtils.encode(a);
        System.out.println("encode =" + encode);
        String decode = DruidEncryptUtils.decode(encode);
        System.out.println("decode =" + decode);
    }
}
