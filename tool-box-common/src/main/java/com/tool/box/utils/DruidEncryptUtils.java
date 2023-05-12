package com.tool.box.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Druid加密工具
 *
 * @Author v_haimiyang
 * @Date 2023/4/19 15:43
 * @Version 1.0
 */
public class DruidEncryptUtils {

    /**
     * 私钥
     */
    private static String privateKey;

    /**
     * 公钥
     */
    private static String publicKey;

    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            privateKey = keyPair[0];
            System.out.println(String.format("privateKey-->%s",privateKey));
            publicKey = keyPair[1];
            System.out.println(String.format("publicKey-->%s",publicKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    @SneakyThrows
    public static String encode(String plaintext){
        System.out.println("明文字符串：" + plaintext);
        String ciphertext = ConfigTools.encrypt(privateKey,plaintext);
        System.out.println("加密后字符串：" + ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    @SneakyThrows
    public static String decode(String ciphertext){
        System.out.println("加密字符串：" + ciphertext);
        String plaintext = ConfigTools.decrypt(publicKey,ciphertext);
        System.out.println("解密后的字符串：" + plaintext);

        return plaintext;
    }

    public static void main(String[] args) {
        String a="123456";
        String encode = DruidEncryptUtils.encode(a);
        System.out.println("encode =" + encode);
        String decode = DruidEncryptUtils.decode(encode);
        System.out.println("decode =" + decode);
    }
}
