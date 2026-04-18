package io.github.loncra.framework.idempotent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.util.LinkedList;
import java.util.List;

/**
 * 幂等配置信息
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.idempotent")
public class IdempotentProperties {

    /**
     * 如果 Idempotent 注解的 value 为空时候，全局忽略的参数类型
     */
    private List<Class<?>> ignoreClasses = new LinkedList<>();

    /**
     * 并发锁 key 前缀
     */
    private String concurrentKeyPrefix = "concurrent:";

    /**
     * 幂等锁 key 前缀
     */
    private String idempotentKeyPrefix = "idempotent:";

    /**
     * 并发切面排序值
     */
    private int concurrentPointcutAdvisorOrderValue = Ordered.LOWEST_PRECEDENCE;

    /**
     * 幂等切面排序值
     */
    private int idempotentPointcutAdvisorOrderValue = Ordered.LOWEST_PRECEDENCE;

    /**
     * 构造函数
     */
    public IdempotentProperties() {
    }

    /**
     * 获取全局忽略的参数类型
     *
     * @return 全局忽略的参数类型
     */
    public List<Class<?>> getIgnoreClasses() {
        return ignoreClasses;
    }

    /**
     * 设置全局忽略的参数类型
     *
     * @param ignoreClasses 全局忽略的参数类型
     */
    void setIgnoreClasses(List<Class<?>> ignoreClasses) {
        this.ignoreClasses = ignoreClasses;
    }

    /**
     * 获取并发锁 key 前缀
     *
     * @return 并发锁 key 前缀
     */
    public String getConcurrentKeyPrefix() {
        return concurrentKeyPrefix;
    }

    /**
     * 设置并发锁 key 前缀
     *
     * @param concurrentKeyPrefix 并发锁 key 前缀
     */
    public void setConcurrentKeyPrefix(String concurrentKeyPrefix) {
        this.concurrentKeyPrefix = concurrentKeyPrefix;
    }

    /**
     * 获取幂等锁 key 前缀
     *
     * @return 幂等锁 key 前缀
     */
    public String getIdempotentKeyPrefix() {
        return idempotentKeyPrefix;
    }

    /**
     * 设置幂等锁 key 前缀
     *
     * @param idempotentKeyPrefix 幂等锁 key 前缀
     */
    public void setIdempotentKeyPrefix(String idempotentKeyPrefix) {
        this.idempotentKeyPrefix = idempotentKeyPrefix;
    }

    /**
     * 获取并发切面排序值
     *
     * @return 并发切面排序值
     */
    public int getConcurrentPointcutAdvisorOrderValue() {
        return concurrentPointcutAdvisorOrderValue;
    }

    /**
     * 设置并发切面排序值
     *
     * @param concurrentPointcutAdvisorOrderValue 并发切面排序值
     */
    public void setConcurrentPointcutAdvisorOrderValue(int concurrentPointcutAdvisorOrderValue) {
        this.concurrentPointcutAdvisorOrderValue = concurrentPointcutAdvisorOrderValue;
    }

    /**
     * 获取幂等切面排序值
     *
     * @return 幂等切面排序值
     */
    public int getIdempotentPointcutAdvisorOrderValue() {
        return idempotentPointcutAdvisorOrderValue;
    }

    /**
     * 设置幂等切面排序值
     *
     * @param idempotentPointcutAdvisorOrderValue 幂等切面排序值
     */
    public void setIdempotentPointcutAdvisorOrderValue(int idempotentPointcutAdvisorOrderValue) {
        this.idempotentPointcutAdvisorOrderValue = idempotentPointcutAdvisorOrderValue;
    }
}
