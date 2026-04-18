package io.github.loncra.framework.security.audit.memory;

import io.github.loncra.framework.security.AuditConfiguration;
import io.github.loncra.framework.security.AuditProperties;
import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

import java.util.stream.Collectors;

/**
 * 自定义内存审计仓库配置
 *
 * @author maurice.chen
 */
@ConditionalOnMissingBean(AuditEventRepository.class)
@EnableConfigurationProperties(AuditProperties.class)
@Conditional(AuditConfiguration.AuditImportSelectorCondition.class)
@ConditionalOnProperty(prefix = "loncra.framework.security.audit", value = "enabled", matchIfMissing = true)
public class CustomInMemoryAuditConfiguration {

    /**
     * 创建自定义内存审计事件仓库 Bean
     *
     * @param interceptors 审计事件仓库拦截器提供者
     *
     * @return AuditEventRepository 实例
     */
    @Bean
    public AuditEventRepository auditEventRepository(ObjectProvider<AuditEventRepositoryWriteInterceptor> interceptors) {
        return new CustomInMemoryAuditEventRepository(
                1000,
                interceptors.stream().collect(Collectors.toList())
        );
    }
}
