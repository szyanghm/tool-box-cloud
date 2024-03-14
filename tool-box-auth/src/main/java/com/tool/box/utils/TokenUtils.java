package com.tool.box.utils;

import com.tool.box.common.Contents;
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
        return redisUtil.del(Contents.PREFIX_USER_TOKEN + account);
    }


}
