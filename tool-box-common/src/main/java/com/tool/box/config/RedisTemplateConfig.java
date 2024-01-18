package com.tool.box.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisTemplate序列化
 *
 * @Author v_haimiyang
 * @Date 2023/7/7 16:34
 * @Version 1.0
 */
@Configuration
public class RedisTemplateConfig {

//    /**
//     * 单独作用用于shiro，但是不要在本例的缓存和session中改动值的序列化方式（即使用默认的jdk方式），否则会在序列化或反序列化时出现错误。
//     *
//     * @param redisConnectionFactory redis连接工厂
//     * @return RedisTemplate
//     */
//    @Bean(name = "redisTemplateForCache")
//    public RedisTemplate<String, Object> redisTemplateForCache(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        RedisSerializer redisSerializer = new StringRedisSerializer();
//        // 设置键（key）的序列化采用StringRedisSerializer。
//        redisTemplate.setKeySerializer(redisSerializer);
//        redisTemplate.setHashKeySerializer(redisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    /**
     * 公用的redis缓存序列化操作
     *
     * @param redisConnectionFactory redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer redisSerializer = new StringRedisSerializer();
        RedisSerializer<Object> genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
