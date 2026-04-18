package io.github.loncra.framework.security.test;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.jackson.deserializer.NameEnumDeserializer;
import io.github.loncra.framework.commons.jackson.deserializer.NameValueEnumDeserializer;
import io.github.loncra.framework.commons.jackson.deserializer.ValueEnumDeserializer;
import io.github.loncra.framework.commons.jackson.serializer.NameEnumSerializer;
import io.github.loncra.framework.commons.jackson.serializer.NameValueEnumSerializer;
import io.github.loncra.framework.commons.jackson.serializer.ValueEnumSerializer;
import io.github.loncra.framework.security.StoragePositionProperties;
import io.github.loncra.framework.security.audit.AuditEventRepositoryQueryInterceptor;
import io.github.loncra.framework.security.audit.AuditEventRepositoryWriteInterceptor;
import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchAuditEventRepository;
import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchQueryGenerator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.stream.Collectors;

@SpringBootApplication
@EnableConfigurationProperties(StoragePositionProperties.class)
public class BasicSecurityStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicSecurityStarterApplication.class, args);
    }

    @Bean
    public ElasticsearchAuditEventRepository elasticsearchAuditEventRepository(
            ElasticsearchOperations elasticsearchOperations,
            StoragePositionProperties storagePositionProperties,
            ElasticsearchQueryGenerator elasticsearchQueryGenerator,
            ObjectProvider<AuditEventRepositoryWriteInterceptor> writeInterceptors,
            ObjectProvider<AuditEventRepositoryQueryInterceptor<BoolQuery.Builder>> queryInterceptors
    ) {

        return new ElasticsearchAuditEventRepository(
                writeInterceptors.stream().collect(Collectors.toList()),
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

    @Bean
    public ObjectMapper loncraFrameworkJacksonJsonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule module = new SimpleModule();

        module.addSerializer(NameValueEnum.class, new NameValueEnumSerializer());
        module.addSerializer(ValueEnum.class, new ValueEnumSerializer());
        module.addSerializer(NameEnum.class, new NameEnumSerializer());

        module.addDeserializer(NameValueEnum.class, new NameValueEnumDeserializer<>());
        module.addDeserializer(ValueEnum.class, new ValueEnumDeserializer<>());
        module.addDeserializer(NameEnum.class, new NameEnumDeserializer<>());
        CastUtils.setObjectMapper(objectMapper);
        return objectMapper;
    }
}
