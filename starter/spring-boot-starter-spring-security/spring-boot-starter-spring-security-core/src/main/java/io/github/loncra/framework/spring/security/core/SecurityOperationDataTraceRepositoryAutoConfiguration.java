package io.github.loncra.framework.spring.security.core;

import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecordHook;
import io.github.loncra.framework.mybatis.plus.MybatisPlusAutoConfiguration;
import io.github.loncra.framework.mybatis.plus.audit.MybatisPlusOperationDataTraceRepository;
import io.github.loncra.framework.spring.security.core.audit.SecurityPrincipalOperationDataTraceRepository;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.audit.creator.OperationDataTraceAuditEventInterceptor;
import io.github.loncra.framework.spring.security.core.authentication.config.SecurityPrincipalDataOwnerProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
@ConditionalOnMissingBean(MybatisPlusOperationDataTraceRepository.class)
@EnableConfigurationProperties(SecurityPrincipalDataOwnerProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.mybatis.operation-data-trace", value = "enabled", matchIfMissing = true)
public class SecurityOperationDataTraceRepositoryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SecurityPrincipalOperationDataTraceRepository principalOperationDataTraceRepository(
            OperationDataTraceProperties operationDataTraceProperties,
            OperationDataTraceAuditEventInterceptor operationDataTraceAuditEventInterceptor,
            ObjectProvider<OperationDataTraceRecordHook> operationDataTraceRecordResolvers
    ) {
        return new SecurityPrincipalOperationDataTraceRepository(
                operationDataTraceProperties,
                operationDataTraceRecordResolvers.stream().toList(),
                operationDataTraceAuditEventInterceptor
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public OperationDataTraceAuditEventInterceptor operationDataTraceAuditEventInterceptor(
            ControllerAuditProperties auditProperties
    ) {
        return new OperationDataTraceAuditEventInterceptor(auditProperties);
    }

}

