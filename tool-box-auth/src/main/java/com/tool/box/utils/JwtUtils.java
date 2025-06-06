package com.tool.box.utils;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tool.box.base.LoginUser;
import com.tool.box.config.SystemConfig;
import com.tool.box.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author v_haimiyang
 * @Date 2023/8/14 10:27
 * @Version 1.0
 */
@Slf4j
public class JwtUtils {

    /**
     * 生成token
     *
     * @param loginUser 用户信息
     * @return 加密的token
     */
    public static String createToken(LoginUser loginUser) {
        JWTSigner signer = JWTSignerUtil.hs256(getSecretKey());
        return JWT.create()
                .setIssuedAt(new Date())
                .setPayload("user", JSONObject.toJSONString(loginUser))
                .setSubject(loginUser.getAccount())
                .setKey(getSecretKey())
                .sign(signer);
    }

    /**
     * 校验token是否正确
     *
     * @param token 登录凭证
     * @return 是否正确
     */
    public static boolean verify(String token) {
        return JWT.of(token).setKey(getSecretKey()).validate(0);
    }

    /**
     * 获得token中的用户信息
     *
     * @param token 登录凭证
     * @return 用户信息
     */
    public static LoginUser getToken(String token) {
        JWT jwt = JWT.of(token).setKey(getSecretKey());
        String user = (String) jwt.getPayload("user");
        log.info("token中的用户信息:" + user);
        return JSONObject.parseObject(user, LoginUser.class);
    }

    /**
     * 获得token中包含了用户账号
     *
     * @param token 登录凭证
     * @return token中的用户账号
     */
    public static String getAccount(String token) {
        JWT jwt = JWT.of(token).setKey(getSecretKey());
        return (String) jwt.getPayload(JWT.SUBJECT);
    }

    /**
     * 密钥
     *
     * @return byte数组密码
     */
    private static byte[] getSecretKey() {
        return SystemConfig.secret.getBytes(StandardCharsets.UTF_8);
    }

    /**
     *
     * @param response
     * @param code
     * @param errorMsg
     */
    public static void responseError(ServletResponse response, Integer code, String errorMsg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // issues/I4YH95浏览器显示乱码问题
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        ResultVO jsonResult = new ResultVO(code, errorMsg);
        OutputStream os = null;
        try {
            os = httpServletResponse.getOutputStream();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(code);
            os.write(new ObjectMapper().writeValueAsString(jsonResult).getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
