package com.tool.box.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author v_haimiyang
 * @Date 2023/8/10 17:15
 * @Version 1.0
 */
@Component
public class RedisUtils {

    private StringRedisTemplate redisTemplate;

    public RedisUtils(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 写入缓存
     *
     * @param key   redis键
     * @param value redis值
     * @return 是否成功
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key        redis键
     * @param value      redis值
     * @param expireTime 秒
     * @return 是否成功
     */
    public boolean set(String key, String value, Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key        redis键
     * @param value      redis值
     * @param expireTime 时间
     * @param timeUnit   时间单位
     * @return 是否成功
     */
    public boolean set(String key, String value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        return redisTemplate.expire(key, time, timeUnit);
    }

    public Long incr(String key, long num) {
        return redisTemplate.opsForValue().increment(key, num);
    }

    public Long incr(String key, long num, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
        return redisTemplate.opsForValue().increment(key, num);
    }


    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean leftPushAllStr(String key, String[] val) {
        boolean result = false;
        try {
            redisTemplate.opsForList().leftPushAll(key, val);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> range(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

}
