package io.github.loncra.framework.minio;

import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.minio.ExpirableBucket;
import io.github.loncra.framework.commons.minio.FileObject;
import io.github.loncra.framework.minio.config.AutoDeleteProperties;
import io.minio.ListObjectsArgs;
import io.minio.Result;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 自动删除配置
 *
 * @author maurice.chen
 */
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(MinioAutoConfiguration.class)
@EnableConfigurationProperties(AutoDeleteProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.minio", value = "enabled", matchIfMissing = true)
public class MinioAutoDeleteConfiguration implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioAutoDeleteConfiguration.class);

    /**
     * MinIO 异步模板
     */
    private final MinioAsyncTemplate minioAsyncTemplate;

    /**
     * 自动删除配置属性
     */
    private final AutoDeleteProperties autoDeleteProperties;

    /**
     * 构造函数
     *
     * @param minioAsyncTemplate   MinIO 异步模板
     * @param autoDeleteProperties 自动删除配置属性
     */
    MinioAutoDeleteConfiguration(
            MinioAsyncTemplate minioAsyncTemplate,
            AutoDeleteProperties autoDeleteProperties
    ) {
        this.minioAsyncTemplate = minioAsyncTemplate;
        this.autoDeleteProperties = autoDeleteProperties;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // FIXME 怎么做成可动态的配置
        taskRegistrar.addCronTask(() -> {

            if (Objects.isNull(autoDeleteProperties.getExpiration())) {
                return;
            }

            for (ExpirableBucket bucket : autoDeleteProperties.getExpiration()) {

                TimeProperties time = bucket.getExpirationTime();

                if (Objects.isNull(time)) {
                    LOGGER.warn("找不到 [{}] 桶的自动删除时间配置。", bucket.getBucketName());
                    continue;
                }

                ListObjectsArgs listObjectsArgs = ListObjectsArgs
                        .builder()
                        .region(bucket.getRegion())
                        .bucket(bucket.getBucketName())
                        .build();

                Iterable<Result<Item>> iterable = minioAsyncTemplate.listObjects(listObjectsArgs);

                for (Result<Item> result : iterable) {

                    try {

                        Item item = result.get();

                        if (item.isDeleteMarker()) {
                            continue;
                        }

                        LocalDateTime expirationTime = item
                                .lastModified()
                                .toLocalDateTime()
                                .plus(time.getValue(), time.getUnit().toChronoUnit());

                        if (LocalDateTime.now().isAfter(expirationTime)) {
                            minioAsyncTemplate.deleteObject(FileObject.of(bucket, item.objectName()), true);
                            LOGGER.info("删除桶 [{}] 的 [{}] 对象", bucket.getBucketName(), item.objectName());
                        }

                    }
                    catch (Exception e) {
                        LOGGER.error("获取对象失败", e);
                    }

                }
            }

        }, autoDeleteProperties.getCron());
    }
}
