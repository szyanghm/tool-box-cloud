package com.tool.box.utils;

import com.tool.box.base.LoginUser;
import com.tool.box.common.Contents;
import com.tool.box.enums.SystemCodeEnum;
import com.tool.box.exception.InternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * token操作工具类
 *
 * @Author v_haimiyang
 * @Date 2023/8/15 15:44
 * @Version 1.0
 */
@Slf4j
@Component
public class TokenUtils {

    @Lazy
    @Resource
    private RedisUtils redisUtil;

    /**
     * token续期的时间(token每7天进行一次续期)
     */
    public static final long EXPIRE_TIME = (7 * 12) * 60 * 60;
    /**
     * token刷新的时间(Token有效期为14天)
     */
    private final static long EXPIRE_TIME_2 = EXPIRE_TIME * 2;

    /**
     * 将token和用户账号写入redis缓存设置时效时间
     *
     * @param token   登录凭证
     * @param account 用户账号
     */
    public void setToken(String token, String account) {
        redisUtil.set(Contents.PREFIX_USER_TOKEN + account, token, EXPIRE_TIME_2);
        redisUtil.set(Contents.PREFIX_USER_TOKEN_TIME + account, account, EXPIRE_TIME);
    }

    /**
     * 计算token续期
     *
     * @param account 用户账号
     * @return true有效/false失效
     */
    public Boolean verifyExpired(String account) {
        String str = redisUtil.get(Contents.PREFIX_USER_TOKEN_TIME + account);
        if (StringUtils.isBlank(str)) {
            //到7天就开始续期
            return false;
        }
        return true;
    }

    /**
     * 获取redis缓存的认证信息
     *
     * @param account 账号
     * @return token
     */
    public String getAuthToken(String account) {
        return redisUtil.get(Contents.PREFIX_USER_TOKEN + account);
    }

    /**
     * 清除redis用户缓存
     *
     * @param account 账号
     * @return true成功/false失败
     */
    public Boolean del(String account) {
        redisUtil.del(Contents.PREFIX_USER_TOKEN_TIME + account);
        redisUtil.del(Contents.USERINFO + account);
        return redisUtil.del(Contents.PREFIX_USER_TOKEN + account);
    }


    /**
     * 校验token&刷新token
     *
     * @param token 登录凭证
     * @return 用户信息
     */
    public LoginUser checkJwtTokenRefresh(String token) {
        if (!JwtUtils.verify(token)) {
            throw new InternalApiException(SystemCodeEnum.INVALID_TOKEN);
        }
        String account;
        try {
            // 解密获得username，用于和数据库进行对比
            account = JwtUtils.getAccount(token);
        } catch (Exception e) {
            throw new InternalApiException(SystemCodeEnum.INVALID_TOKEN);
        }
        if (StringUtils.isBlank(account)) {
            throw new InternalApiException(SystemCodeEnum.TOKEN_EXCEPTION);
        }
        //如果redis取不到表示用户过期了
        String authToken = this.getAuthToken(account);
        if (StringUtils.isBlank(authToken)) {
            throw new InternalApiException(SystemCodeEnum.USER_LOGIN_EXPIRED);
        }
        LoginUser loginUser = JwtUtils.getToken(authToken);
        // 拿到了，校验token有效性
        if (!this.verifyExpired(account)) {
            //token续期
            this.setToken(authToken, account);
            log.debug("——————————用户在线操作，更新token保证不掉线—————————jwtTokenRefresh——————— " + token);
        } else {
            if (loginUser == null) {
                throw new InternalApiException(SystemCodeEnum.USER_DOES_NOT_EXIST);
            }
            if (loginUser.getStatus() != 0) {
                throw new InternalApiException(SystemCodeEnum.USER_LOCK_ING);
            }
        }
        return loginUser;
    }


}
