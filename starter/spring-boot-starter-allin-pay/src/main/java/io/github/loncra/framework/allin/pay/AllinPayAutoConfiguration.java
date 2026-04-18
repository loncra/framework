package io.github.loncra.framework.allin.pay;

import io.github.loncra.framework.allin.pay.config.AllInPayProperties;
import io.github.loncra.framework.allin.pay.service.AllInPayService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 通联支付自动配置
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties(AllInPayProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.allin-pay", value = "enabled", matchIfMissing = true)
public class AllinPayAutoConfiguration {

    /**
     * 创建通联支付服务 Bean
     *
     * @param properties   通联支付配置属性
     * @param restTemplate REST 模板提供者
     *
     * @return AllInPayService 实例
     */
    @Bean
    public AllInPayService allInPayService(
            AllInPayProperties properties,
            ObjectProvider<RestTemplate> restTemplate
    ) {
        return new AllInPayService(properties, restTemplate.getIfAvailable(RestTemplate::new));
    }
}
