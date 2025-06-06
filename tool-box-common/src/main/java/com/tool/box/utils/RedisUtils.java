package com.tool.box.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author v_haimiyang
 * @Date 2023/8/10 17:15
 * @Version 1.0
 */
@Slf4j
@Component
public class RedisUtils {

    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "redisTemplateALL")
    private RedisTemplate<String, Object> redisTemplateArr;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 写入缓存
     *
     * @param key   redis键
     * @param value redis值
     * @return 是否成功
     */
    public <T> boolean set(final String key, T value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key        redis键
     * @param value      redis值
     * @param expireTime 秒
     * @return 是否成功
     */
    public <T> boolean set(String key, T value, Long expireTime) {
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
    public <T> boolean set(String key, T value, Long expireTime, TimeUnit timeUnit) {
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
        return stringRedisTemplate.opsForValue().increment(key, num);
    }

    public Long incr(String key, long num, long time, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, time, timeUnit);
        return stringRedisTemplate.opsForValue().increment(key, num);
    }

    public Boolean leftPushAllStr(String key, String[] val) {
        boolean result = false;
        try {
            stringRedisTemplate.opsForList().leftPushAll(key, val);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> Boolean leftPushAll(String key, List<T> list) {
        boolean result = false;
        try {
            delete(key);
            redisTemplateArr.opsForList().leftPushAll(key, list.toArray());
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> List<T> range(String key) {
        return (List<T>) redisTemplateArr.opsForList().range(key, 0, -1);
    }

    public void trim(String key) {
        redisTemplateArr.opsForList().trim(key, 0, 0);
    }

    public void remove(String key) {
        Long count = redisTemplateArr.opsForList().remove(key, 0, redisTemplateArr.opsForList().size(key));
        log.info("共移除了{}个元素", count);
    }

    public void delete(String key) {
        redisTemplateArr.delete(key);
    }

    public List<String> rangeStr(String key) {
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

}
