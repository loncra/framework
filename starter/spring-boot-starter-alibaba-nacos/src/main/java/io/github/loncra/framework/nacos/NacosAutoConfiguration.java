package io.github.loncra.framework.nacos;


import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import io.github.loncra.framework.nacos.event.NacosDiscoveryEventProperties;
import io.github.loncra.framework.nacos.event.NacosServiceListenerValidator;
import io.github.loncra.framework.nacos.event.NacosSpringEventManager;
import io.github.loncra.framework.nacos.task.NacosCronScheduledListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * nacos 自动配置类
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureAfter(NacosConfigAutoConfiguration.class)
@EnableConfigurationProperties(NacosDiscoveryEventProperties.class)
@ConditionalOnProperty(prefix = "spring.cloud.nacos.discovery", value = "enabled", matchIfMissing = true)
public class NacosAutoConfiguration {

    /**
     * 创建 Nacos Cron 调度监听器 Bean
     *
     * @param nacosConfigManager Nacos 配置管理器
     *
     * @return NacosCronScheduledListener 实例
     */
    @Bean
    @ConditionalOnMissingBean(NacosCronScheduledListener.class)
    @ConditionalOnProperty(prefix = "spring.cloud.nacos.config.schedule", value = "enabled", matchIfMissing = true)
    public NacosCronScheduledListener nacosCronScheduledListener(NacosConfigManager nacosConfigManager) {
        return new NacosCronScheduledListener(nacosConfigManager);
    }

    /**
     * 创建 Nacos Spring 事件管理器 Bean
     *
     * @param nacosServiceManager               Nacos 服务管理器
     * @param nacosDiscoveryEventProperties     Nacos 服务发现事件配置属性
     * @param nacosServiceListenerValidatorList Nacos 服务监听器校验器列表
     *
     * @return NacosSpringEventManager 实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cloud.nacos.discovery.event", value = "enabled", havingValue = "true")
    public NacosSpringEventManager nacosServiceEventManager(
            NacosServiceManager nacosServiceManager,
            NacosDiscoveryProperties nacosDiscoveryProperties,
            NacosDiscoveryEventProperties nacosDiscoveryEventProperties,
            List<NacosServiceListenerValidator> nacosServiceListenerValidatorList
    ) {

        return new NacosSpringEventManager(
                nacosServiceManager,
                nacosDiscoveryProperties,
                nacosDiscoveryEventProperties,
                nacosServiceListenerValidatorList
        );

    }
}
