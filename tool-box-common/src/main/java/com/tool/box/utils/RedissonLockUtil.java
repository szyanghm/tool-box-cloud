package com.tool.box.utils;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redisson锁工具类
 *
 * @Author v_haimiyang
 * @Date 2025/5/30 15:12
 * @Version 1.0
 */
@Component
public class RedissonLockUtil {
    private static RedissonClient redissonClient;

    @Autowired
    public RedissonLockUtil(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 获取锁对象
     *
     * @param lockKey 锁的键名
     * @return RLock 锁对象
     */
    public static RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 尝试获取锁（默认等待时间10秒，锁过期时间30秒）
     *
     * @param lockKey 锁的键名
     * @return 是否获取成功
     */
    public static boolean tryLock(String lockKey) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(10, 30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 尝试获取锁（默认等待时间10秒，锁过期时间30秒）
     *
     * @param lockKey 锁的键名
     * @return 是否获取成功
     */
    public static boolean tryLock(String lockKey,Long expireTime) {
        RLock lock = getLock(lockKey);
        try {
            return lock.tryLock(10, expireTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁的键名
     */
    public static void unlock(String lockKey) {
        RLock lock = getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
