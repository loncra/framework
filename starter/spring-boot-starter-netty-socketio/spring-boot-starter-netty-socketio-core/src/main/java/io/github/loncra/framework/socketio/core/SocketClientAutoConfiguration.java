package io.github.loncra.framework.socketio.core;

import io.github.loncra.framework.socketio.core.holder.SocketMessagePointcutAdvisor;
import io.github.loncra.framework.socketio.core.holder.interceptor.SocketMessageInterceptor;
import io.github.loncra.framework.socketio.core.interceptor.AuthorizationInterceptor;
import io.github.loncra.framework.socketio.core.interceptor.SocketServerInterceptor;
import io.github.loncra.framework.socketio.core.resolver.AckMessageSenderResolver;
import io.github.loncra.framework.socketio.core.resolver.MessageSenderResolver;
import io.github.loncra.framework.socketio.core.resolver.support.BroadcastMessageSenderResolver;
import io.github.loncra.framework.socketio.core.resolver.support.MultipleUnicastMessageSender;
import io.github.loncra.framework.socketio.core.resolver.support.UnicastMessageSender;
import io.github.loncra.framework.spring.security.core.authentication.AccessTokenContextRepository;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.web.SpringWebMvcAutoConfiguration;
import io.github.loncra.framework.spring.web.config.SpringWebMvcProperties;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * Socket 服务端自动配置类。
 * <p>
 * 负责注册 Socket 服务管理器、消息发送器以及消息切面相关 Bean。
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureBefore(SpringWebMvcAutoConfiguration.class)
@EnableConfigurationProperties({SpringWebMvcProperties.class, AuthenticationProperties.class, SocketProperties.class})
@ConditionalOnProperty(prefix = "loncra.framework.socketio", value = "enabled", matchIfMissing = true)
public class SocketClientAutoConfiguration {

    /**
     * 创建 Socket 服务管理器 Bean。
     *
     * @param socketProperties Socket 配置属性
     * @param redissonClient Redisson 客户端
     * @param ackMessageSenderResolvers ACK 消息发送解析器提供者
     * @param messageSenderResolvers 普通消息发送解析器提供者
     * @param socketServerInterceptors Socket 服务生命周期拦截器提供者
     * @param authorizationInterceptors 认证拦截器提供者
     * @return Socket 服务管理器实例
     */
    @Bean
    @ConditionalOnMissingBean(SocketServerManager.class)
    public SocketServerManager getSocketServerManager(
            SocketProperties socketProperties,
            AccessTokenContextRepository accessTokenContextRepository,
            RedissonClient redissonClient,
            ObjectProvider<AckMessageSenderResolver<?>> ackMessageSenderResolvers,
            ObjectProvider<MessageSenderResolver> messageSenderResolvers,
            ObjectProvider<SocketServerInterceptor> socketServerInterceptors,
            ObjectProvider<AuthorizationInterceptor> authorizationInterceptors
    ) {
        return new SocketServerManager(
                socketProperties,
                redissonClient,
                accessTokenContextRepository,
                messageSenderResolvers.stream().toList(),
                ackMessageSenderResolvers.stream().toList(),
                socketServerInterceptors.stream().toList(),
                authorizationInterceptors.stream().toList()
        );
    }

    /**
     * 创建 Socket 结果响应体增强 Bean
     *
     * @param properties          Spring Web MVC 配置属性
     * @param socketServerManager Socket 服务管理
     *
     * @return Socket 结果响应体增强实例
     */
    @Bean
    @ConditionalOnMissingBean(SocketResultResponseBodyAdvice.class)
    public SocketResultResponseBodyAdvice socketResultResponseBodyAdvice(
            SpringWebMvcProperties properties,
            SocketServerManager socketServerManager
    ) {
        return new SocketResultResponseBodyAdvice(properties, socketServerManager);
    }

    /**
     * 创建 Socket 消息拦截器 Bean
     *
     * @param socketServerManager Socket 服务管理
     *
     * @return Socket 消息拦截器实例
     */
    @Bean
    @ConditionalOnMissingBean(SocketMessageInterceptor.class)
    public SocketMessageInterceptor socketMessageInterceptor(SocketServerManager socketServerManager) {
        return new SocketMessageInterceptor(socketServerManager);
    }

    /**
     * 创建 Socket 消息切面顾问 Bean
     *
     * @param socketMessageInterceptor Socket 消息拦截器
     *
     * @return Socket 消息切面顾问实例
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public SocketMessagePointcutAdvisor socketMessagePointcutAdvisor(SocketMessageInterceptor socketMessageInterceptor) {
        return new SocketMessagePointcutAdvisor(socketMessageInterceptor);
    }

    @Bean
    public UnicastMessageSender unicastMessageSender() {
        return new UnicastMessageSender();
    }

    @Bean
    public BroadcastMessageSenderResolver broadcastMessageSenderResolver() {
        return new BroadcastMessageSenderResolver();
    }

    @Bean
    public MultipleUnicastMessageSender multipleUnicastMessageSender(SocketProperties config){
        return new MultipleUnicastMessageSender(config);
    }
}
