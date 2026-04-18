package io.github.loncra.framework.security.audit.mongo;

import io.github.loncra.framework.security.AuditConfiguration;
import io.github.loncra.framework.security.AuditProperties;
import io.github.loncra.framework.security.StoragePositionProperties;
import io.github.loncra.framework.security.audit.AuditEventRepositoryQueryInterceptor;
import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import io.github.loncra.framework.security.audit.ExtendAuditEventRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.stream.Collectors;

/**
 * Mongo 审计仓库配置
 *
 * @author maurice.chen
 */
@ConditionalOnClass(MongoTemplate.class)
@ConditionalOnMissingBean(AuditEventRepository.class)
@EnableConfigurationProperties(AuditProperties.class)
@Conditional(AuditConfiguration.AuditImportSelectorCondition.class)
@ConditionalOnProperty(prefix = "loncra.framework.security.audit", value = "enabled", matchIfMissing = true)
public class MongoAuditConfiguration {

    /**
     * 创建 Mongo 审计事件仓库 Bean
     *
     * @param mongoTemplate             Mongo 模板
     * @param storagePositionProperties 存储位置配置属性
     * @param interceptors              审计事件仓库拦截器提供者
     *
     * @return ExtendAuditEventRepository 实例
     */
    @Bean
    public ExtendAuditEventRepository auditEventRepository(
            MongoTemplate mongoTemplate,
            StoragePositionProperties storagePositionProperties,
            ObjectProvider<AuditEventRepositoryWriteInterceptor> interceptors,
            ObjectProvider<AuditEventRepositoryQueryInterceptor<Criteria>> queryInterceptors
    ) {

        return new MongoAuditEventRepository(
                interceptors.stream().collect(Collectors.toList()),
                queryInterceptors.stream().collect(Collectors.toList()),
                mongoTemplate,
                storagePositionProperties
        );

    }
}