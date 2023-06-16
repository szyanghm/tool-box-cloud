package com.tool.box.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis实现CacheManager
 *
 * @Author v_haimiyang
 * @Date 2023/6/16 16:12
 * @Version 1.0
 */
public class ShiroRedisCacheManager implements CacheManager {
    /**
     * redis使用模板
     */
    private RedisTemplate<String, Object> redisTemplate;

    public ShiroRedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache<>(name, redisTemplate);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
