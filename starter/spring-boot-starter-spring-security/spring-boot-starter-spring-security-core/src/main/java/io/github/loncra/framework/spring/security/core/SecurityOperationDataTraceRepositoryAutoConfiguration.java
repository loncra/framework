package io.github.loncra.framework.spring.security.core;

import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.plus.MybatisPlusAutoConfiguration;
import io.github.loncra.framework.mybatis.plus.audit.MybatisPlusOperationDataTraceResolver;
import io.github.loncra.framework.spring.security.core.audit.SecurityPrincipalOperationDataTraceResolver;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.authentication.config.SecurityPrincipalDataOwnerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
@ConditionalOnMissingBean(MybatisPlusOperationDataTraceResolver.class)
@EnableConfigurationProperties(SecurityPrincipalDataOwnerProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.mybatis.operation-data-trace", value = "enabled", matchIfMissing = true)
public class SecurityOperationDataTraceRepositoryAutoConfiguration {

    @Bean
    public SecurityPrincipalOperationDataTraceResolver principalOperationDataTraceRepository(
            OperationDataTraceProperties operationDataTraceProperties,
            ControllerAuditProperties controllerAuditProperties
    ) {
        return new SecurityPrincipalOperationDataTraceResolver(operationDataTraceProperties, controllerAuditProperties);
    }

}

