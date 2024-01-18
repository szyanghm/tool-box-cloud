package com.tool.box.utils;

import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Objects;

/**
 * 密码加解密工具类
 *
 * @Author v_haimiyang
 * @Date 2023/8/16 17:27
 * @Version 1.0
 */
@Slf4j
public class PasswordUtil {

    /**
     * 定义使用的算法为:PBEWITHMD5andDES算法
     * JAVA6支持以下任意一种算法
     * PBEWITHMD5ANDDES
     * PBEWITHMD5ANDTRIPLEDES
     * PBEWITHSHAANDDESEDE
     * PBEWITHSHA1ANDRC2_40
     * PBKDF2WITHHMACSHA1
     * 加密算法
     */
    public static final String ALGORITHM = "PBEWithMD5AndDES";

    /**
     * 定义迭代次数为1000次
     */
    private static final int ITERATIONCOUNT = 1000;

    /**
     * 根据PBE密码生成一把密钥
     *
     * @param password 生成密钥时所使用的密码
     * @return Key PBE算法密钥
     */
    private static Key getPbeKey(String password) {
        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return secretKey;
    }

    /**
     * 加密明文字符串
     *
     * @param account  账号
     * @param password 生成密钥时所使用的密码
     * @param salt     盐值
     * @return 加密后的密文字符串
     */
    public static String encrypt(String account, String password, String salt) {
        Key key = getPbeKey(password);
        byte[] encipheredData;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATIONCOUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            encipheredData = cipher.doFinal(account.getBytes("utf-8"));
        } catch (Exception e) {
            log.error(SystemUtils.getMsg(SystemCodeEnum.PASSWORD_ENCRYPT_FAILED, e.getMessage()));
            throw new InternalApiException(SystemCodeEnum.PASSWORD_ENCRYPT_FAILED);
        }
        return bytesToHexString(encipheredData);
    }

    public static void main(String[] args) {
        String password = encrypt("admin", "12345611", "63293188");
        System.out.println(password);
        System.out.println(decrypt("admin", password, "63293188"));
    }

    /**
     * 解密密文字符串
     *
     * @param account  用户账号
     * @param password 生成密钥时所使用的密码(如需解密,该参数需要与加密时使用的一致)
     * @param salt     盐值(如需解密,该参数需要与加密时使用的一致)
     * @return 解密后的明文字符串
     */
    public static String decrypt(String account, String password, String salt) {
        Key key = getPbeKey(password);
        byte[] passDec;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATIONCOUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            passDec = cipher.doFinal(hexStringToBytes(account));
        } catch (Exception e) {
            log.error(SystemUtils.getMsg(SystemCodeEnum.PASSWORD_DECRYPT_FAILED, e.getMessage()));
            throw new InternalApiException(SystemCodeEnum.PASSWORD_DECRYPT_FAILED);
        }
        return new String(passDec);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param src 字节数组
     */
//    public static String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder();
//        if (src == null || src.length == 0) {
//            return null;
//        }
//        for (byte b : src) {
//            int v = b & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }

    /**
     * 将十六进制字符串转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (StringUtils.isBlank(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
