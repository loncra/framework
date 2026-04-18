package io.github.loncra.framework.idempotent.config;

import io.github.loncra.framework.idempotent.advisor.IdempotentInterceptor;
import io.github.loncra.framework.idempotent.advisor.IdempotentPointcutAdvisor;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentPointcutAdvisor;
import io.github.loncra.framework.idempotent.generator.SpelExpressionValueGenerator;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfigurationV4;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;


/**
 * 幂等性自动配置
 *
 * @author maurice.chen
 */
@Configuration
@ConditionalOnClass(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfigurationV4.class)
@EnableConfigurationProperties(IdempotentProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.idempotent", value = "enabled", matchIfMissing = true)
public class IdempotentAutoConfiguration {

    /**
     * 创建并发拦截器 Bean
     *
     * @param redissonClient       Redisson 客户端
     * @param idempotentProperties 幂等配置属性
     *
     * @return ConcurrentInterceptor 实例
     */
    @Bean
    @ConditionalOnMissingBean(ConcurrentInterceptor.class)
    public ConcurrentInterceptor concurrentInterceptor(
            RedissonClient redissonClient,
            IdempotentProperties idempotentProperties
    ) {
        SpelExpressionValueGenerator generator = new SpelExpressionValueGenerator();
        generator.setPrefix(idempotentProperties.getConcurrentKeyPrefix());
        return new ConcurrentInterceptor(redissonClient, generator);
    }

    /**
     * 创建并发切面顾问 Bean
     *
     * @param concurrentInterceptor 并发拦截器
     * @param idempotentProperties  幂等配置属性
     *
     * @return ConcurrentPointcutAdvisor 实例
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ConcurrentPointcutAdvisor concurrentPointcutAdvisor(
            ConcurrentInterceptor concurrentInterceptor,
            IdempotentProperties idempotentProperties
    ) {
        ConcurrentPointcutAdvisor concurrentPointcutAdvisor = new ConcurrentPointcutAdvisor(concurrentInterceptor);
        concurrentPointcutAdvisor.setOrder(idempotentProperties.getConcurrentPointcutAdvisorOrderValue());

        return concurrentPointcutAdvisor;
    }

    /**
     * 创建幂等拦截器 Bean
     *
     * @param redissonClient       Redisson 客户端
     * @param idempotentProperties 幂等配置属性
     *
     * @return IdempotentInterceptor 实例
     */
    @Bean
    @ConditionalOnMissingBean(IdempotentInterceptor.class)
    public IdempotentInterceptor idempotentInterceptor(
            RedissonClient redissonClient,
            IdempotentProperties idempotentProperties
    ) {
        SpelExpressionValueGenerator generator = new SpelExpressionValueGenerator();
        generator.setPrefix(idempotentProperties.getIdempotentKeyPrefix());
        return new IdempotentInterceptor(redissonClient, generator, idempotentProperties);
    }

    /**
     * 创建幂等切面顾问 Bean
     *
     * @param idempotentInterceptor 幂等拦截器
     * @param idempotentProperties  幂等配置属性
     *
     * @return IdempotentPointcutAdvisor 实例
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public IdempotentPointcutAdvisor idempotentPointcutAdvisor(
            IdempotentInterceptor idempotentInterceptor,
            IdempotentProperties idempotentProperties
    ) {
        IdempotentPointcutAdvisor idempotentPointcutAdvisor = new IdempotentPointcutAdvisor(idempotentInterceptor);
        idempotentPointcutAdvisor.setOrder(idempotentProperties.getIdempotentPointcutAdvisorOrderValue());
        return idempotentPointcutAdvisor;
    }

}
