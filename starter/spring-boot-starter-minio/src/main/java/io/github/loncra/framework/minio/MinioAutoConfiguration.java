package io.github.loncra.framework.minio;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.loncra.framework.minio.config.MinioProperties;
import io.minio.MinioAsyncClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio 自动配置类
 *
 * @author maurice.chen
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.minio", value = "enabled", matchIfMissing = true)
public class MinioAutoConfiguration {

    /**
     * minio 模版配置
     *
     * @param minioProperties minio 配置信息
     * @param objectMapper jackson json 序列化对象映射类
     *
     * @return minio 模版
     */
    @Bean
    @ConditionalOnMissingBean(MinioAsyncTemplate.class)
    public MinioAsyncTemplate minioAsyncTemplate(MinioProperties minioProperties,
                                                 ObjectProvider<ObjectMapper> objectMapper) {

        MinioAsyncClient minioAsyncClient = MinioAsyncClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();

        return new MinioAsyncTemplate(minioAsyncClient, objectMapper.getIfUnique(ObjectMapper::new), minioProperties);

    }

}
