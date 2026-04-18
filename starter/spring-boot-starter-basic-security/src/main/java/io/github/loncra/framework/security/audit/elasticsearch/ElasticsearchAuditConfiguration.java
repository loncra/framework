package io.github.loncra.framework.security.audit.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import io.github.loncra.framework.security.AuditConfiguration;
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
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.stream.Collectors;

/**
 * Elasticsearch 审计仓库配置
 *
 * @author maurice.chen
 */
@ConditionalOnClass(ElasticsearchOperations.class)
@ConditionalOnMissingBean(AuditEventRepository.class)
@EnableConfigurationProperties(StoragePositionProperties.class)
@Conditional(AuditConfiguration.AuditImportSelectorCondition.class)
@ConditionalOnProperty(prefix = "loncra.framework.security.audit", value = "enabled", matchIfMissing = true)
public class ElasticsearchAuditConfiguration {

    /**
     * 创建 Elasticsearch 审计事件仓库 Bean
     *
     * @param elasticsearchOperations   Elasticsearch 操作对象
     * @param storagePositionProperties 存储位置配置属性
     * @param interceptors              审计事件仓库拦截器提供者
     *
     * @return ExtendAuditEventRepository 实例
     */
    @Bean
    public ExtendAuditEventRepository auditEventRepository(
            ElasticsearchOperations elasticsearchOperations,
            StoragePositionProperties storagePositionProperties,
            ElasticsearchQueryGenerator elasticsearchQueryGenerator,
            ObjectProvider<AuditEventRepositoryWriteInterceptor> interceptors,
            ObjectProvider<AuditEventRepositoryQueryInterceptor<BoolQuery.Builder>> queryInterceptors
    ) {

        return new ElasticsearchAuditEventRepository(
                interceptors.stream().collect(Collectors.toList()),
                queryInterceptors.stream().collect(Collectors.toList()),
                elasticsearchOperations,
                elasticsearchQueryGenerator,
                storagePositionProperties
        );

    }

    @Bean
    public ElasticsearchQueryGenerator elasticsearchQueryGenerator() {
        return new ElasticsearchQueryGenerator();
    }
}
