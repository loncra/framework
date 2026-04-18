package io.github.loncra.framework.wechat;

import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.idempotent.config.IdempotentAutoConfiguration;
import io.github.loncra.framework.wechat.service.WechatAppletService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信自动配置类
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureAfter(IdempotentAutoConfiguration.class)
@ConditionalOnProperty(prefix = "loncra.framework.wechat", value = "enabled", matchIfMissing = true)
@EnableConfigurationProperties({AppletProperties.class, WechatProperties.class, OfficialProperties.class})
public class WechatAutoConfiguration {

    /**
     * 创建微信小程序服务 Bean
     *
     * @param appletProperties      小程序配置属性
     * @param wechatProperties      微信配置属性
     * @param concurrentInterceptor 并发拦截器
     *
     * @return 微信小程序服务实例
     */
    @Bean
    public WechatAppletService wechatAppletService(
            AppletProperties appletProperties,
            WechatProperties wechatProperties,
            ConcurrentInterceptor concurrentInterceptor
    ) {

        return new WechatAppletService(appletProperties, wechatProperties, concurrentInterceptor);
    }

}
