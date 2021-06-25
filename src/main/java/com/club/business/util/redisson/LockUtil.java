package com.club.business.util.redisson;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁工具类
 *
 * @author Tom
 * @date 2019-11-15
 */
public class LockUtil {

    private static Locker locker;

    /**
     * 设置工具类使用的locker
     * @param locker
     */
    public static void setLocker(Locker locker) {
        LockUtil.locker = locker;
    }

    /**
     * 获取锁
     * @param lockKey 唯一加锁字段
     */
    public static void lock(String lockKey) {
        locker.lock(lockKey);
    }

    /**
     * 释放锁
     * @param lockKey 唯一加锁字段
     */
    public static void unlock(String lockKey) {
        locker.unlock(lockKey);
    }

    /**
     * 获取锁，超时释放
     * @param lockKey 唯一加锁字段
     * @param timeout 超时时间(到超时时间后会自动释放锁)
     */
    public static void lock(String lockKey, int timeout) {
        locker.lock(lockKey, timeout);
    }

    /**
     * 获取锁，超时释放，指定时间单位
     * @param lockKey 唯一加锁字段
     * @param unit 时间设置字段(一般使用TimeUnit.SECONDS,表示秒)
     * @param timeout 超时时间(到超时时间后会自动释放锁)
     */
    public static void lock(String lockKey, TimeUnit unit, int timeout) {
        locker.lock(lockKey, unit, timeout);
    }

    /**
     * 尝试获取锁，获取到立即返回true,获取失败立即返回false
     * @param lockKey 唯一加锁字段
     * @return
     */
    public static boolean tryLock(String lockKey) {
        return locker.tryLock(lockKey);
    }

    /**
     * 尝试获取锁，在给定的waitTime时间内尝试，获取到返回true,获取失败返回false,获取到后再给定的leaseTime时间超时释放
     * @param lockKey 唯一加锁字段
     * @param waitTime 等待时间(此时间段会获取锁)
     * @param leaseTime 超时时间(获取到锁后,如果到达超时时间还没有释放锁,将会自动释放锁)
     * @param unit 时间设置字段(一般使用TimeUnit.SECONDS,表示秒)
     * @return
     * @throws InterruptedException
     */
    public static boolean tryLock(String lockKey, long waitTime, long leaseTime,
                                  TimeUnit unit) throws InterruptedException {
        return locker.tryLock(lockKey, waitTime, leaseTime, unit);
    }

    /**
     * 锁释放被任意一个线程持有
     * @param lockKey 唯一加锁字段
     * @return
     */
    public static boolean isLocked(String lockKey) {
        return locker.isLocked(lockKey);
    }
}
