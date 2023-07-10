package com.tool.box.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * shiro的redis缓存管理
 *
 * @Author v_haimiyang
 * @Date 2023/7/7 11:39
 * @Version 1.0
 */
public class RedisCacheManager implements CacheManager {

    /**
     * redis使用模板
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        return new RedisCache<K, V>(cacheName, redisTemplate);
    }
}
