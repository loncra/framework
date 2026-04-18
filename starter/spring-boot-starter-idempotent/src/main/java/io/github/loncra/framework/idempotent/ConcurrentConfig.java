package io.github.loncra.framework.idempotent;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.idempotent.annotation.Concurrent;

/**
 * 并发配置
 *
 * @author maurice.chen
 */
public class ConcurrentConfig {

    /**
     * 键值
     */
    private String key;

    /**
     * 锁类型
     *
     */
    private LockType lockType = LockType.Lock;

    /**
     * 异常信息
     *
     */
    private String exception = ConcurrentInterceptor.DEFAULT_EXCEPTION;

    /**
     * 锁等待时间
     *
     */
    private TimeProperties waitTime = TimeProperties.ofSeconds(1);

    /**
     * 锁生存/释放时间
     *
     */
    private TimeProperties leaseTime = TimeProperties.ofSeconds(5);

    /**
     * 创建一个新的并发配置
     *
     * @param key 键值
     */
    public ConcurrentConfig(String key) {
        this.key = key;
    }

    /**
     * 构造函数
     *
     * @param key      键值
     * @param lockType 锁类型
     */
    public ConcurrentConfig(
            String key,
            LockType lockType
    ) {
        this.key = key;
        this.lockType = lockType;
    }

    /**
     * 构造函数
     *
     * @param key       键值
     * @param lockType  锁类型
     * @param exception 异常信息
     */
    public ConcurrentConfig(
            String key,
            LockType lockType,
            String exception
    ) {
        this.key = key;
        this.lockType = lockType;
        this.exception = exception;
    }

    /**
     * 构造函数
     *
     * @param key       键值
     * @param lockType  锁类型
     * @param exception 异常信息
     * @param waitTime  锁等待时间
     */
    public ConcurrentConfig(
            String key,
            LockType lockType,
            String exception,
            TimeProperties waitTime
    ) {
        this.key = key;
        this.lockType = lockType;
        this.exception = exception;
        this.waitTime = waitTime;
    }

    /**
     * 构造函数
     *
     * @param key       键值
     * @param lockType  锁类型
     * @param exception 异常信息
     * @param waitTime  锁等待时间
     * @param leaseTime 锁释放时间
     */
    public ConcurrentConfig(
            String key,
            LockType lockType,
            String exception,
            TimeProperties waitTime,
            TimeProperties leaseTime
    ) {
        this.key = key;
        this.lockType = lockType;
        this.exception = exception;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
    }

    /**
     * 创建一个新的并发配置
     */
    public ConcurrentConfig() {
    }

    /**
     * 获取键值
     *
     * @return 键值
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置键值
     *
     * @param key 键值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取锁类型
     *
     * @return 锁类型
     */
    public LockType getLockType() {
        return lockType;
    }

    /**
     * 设置锁类型
     *
     * @param lockType 锁类型
     */
    public void setLockType(LockType lockType) {
        this.lockType = lockType;
    }

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    public String getException() {
        return exception;
    }

    /**
     * 设置异常信息
     *
     * @param exception 异常信息
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * 获取锁等待时间
     *
     * @return 锁等待时间
     */
    public TimeProperties getWaitTime() {
        return waitTime;
    }

    /**
     * 设置锁等待时间
     *
     * @param waitTime 锁等待时间
     */
    public void setWaitTime(TimeProperties waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * 获取释放锁时间
     *
     * @return 释放锁时间
     */
    public TimeProperties getLeaseTime() {
        return leaseTime;
    }

    /**
     * 设置释放锁时间
     *
     * @param leaseTime 释放锁时间
     */
    public void setLeaseTime(TimeProperties leaseTime) {
        this.leaseTime = leaseTime;
    }

    /**
     * 根据当前名称创建一个带有后缀的新并发配置
     *
     * @param suffix 后缀值
     *
     * @return 新的配发配置，key 为:当前实例名称 + 后缀值，如果当前实例名称名称没有 ":" 符号，会自动加上。
     */
    public ConcurrentConfig ofSuffix(Object suffix) {
        String name = new CacheProperties(getKey()).getName(suffix);
        ConcurrentConfig newOne = CastUtils.of(this, ConcurrentConfig.class);
        newOne.setKey(name);
        return newOne;
    }

    /**
     * 从并发注解创建并发配置
     *
     * @param concurrent 并发注解
     *
     * @return 并发配置
     */
    public static ConcurrentConfig ofConcurrent(Concurrent concurrent) {
        ConcurrentConfig config = new ConcurrentConfig();

        config.setKey(concurrent.value());
        config.setException(concurrent.exception());
        config.setLockType(concurrent.type());
        config.setLeaseTime(TimeProperties.of(concurrent.leaseTime().value(), concurrent.leaseTime().unit()));
        config.setWaitTime(TimeProperties.of(concurrent.waitTime().value(), concurrent.waitTime().unit()));

        return config;
    }
}
