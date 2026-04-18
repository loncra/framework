package io.github.loncra.framework.datasource;

import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.datasource.config.LoncraDataSourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源自动配置
 * <p>
 * 使用 Spring Boot 的 DataSourceProperties 来创建数据源，支持所有 Spring Boot 支持的数据源配置选项
 *
 * @author maurice.chen
 */
@Configuration
@AutoConfigureBefore(org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(LoncraDataSourceProperties.class)
@ConditionalOnProperty(prefix = "loncra.framework.datasource", value = "enabled", matchIfMissing = true)
public class DataSourceAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAutoConfiguration.class);

    /**
     * 创建读写分离数据源
     *
     * @param properties 数据源配置属性
     * @return 读写分离数据源
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "dataSource")
    public DataSource dataSource(LoncraDataSourceProperties properties) {
        if (!properties.isEnabled()) {
            throw new SystemException("数据源未启用，请设置 loncra.framework.datasource.enabled=true");
        }

        DataSourceProperties masterConfig = properties.getMaster();
        if (masterConfig == null) {
            throw new SystemException("主数据源配置不能为空，请配置 loncra.framework.datasource.master");
        }

        // 创建主数据源
        DataSource masterDataSource = createDataSource(masterConfig, "主数据源");

        // 创建从数据源列表
        List<DataSource> slaveDataSources = new ArrayList<>();
        List<DataSourceProperties> slaveConfigs = properties.getSlaves();
        if (slaveConfigs != null && !slaveConfigs.isEmpty()) {
            for (int i = 0; i < slaveConfigs.size(); i++) {
                DataSourceProperties slaveConfig = slaveConfigs.get(i);
                DataSource slaveDataSource = createDataSource(slaveConfig, "从数据源[" + i + "]");
                slaveDataSources.add(slaveDataSource);
            }
        } else {
            LOGGER.warn("未配置从数据源，所有操作将使用主数据源");
        }

        // 创建读写分离数据源
        ReadWriteSeparateDataSource readWriteSeparateDataSource = new ReadWriteSeparateDataSource(
                masterDataSource,
                slaveDataSources
        );

        LOGGER.info("读写分离数据源初始化完成，主数据源: {}, 从数据源数量: {}",
                    masterConfig.getUrl(),
                    slaveDataSources.size());

        return readWriteSeparateDataSource;
    }

    /**
     * 创建数据源
     * <p>
     * 使用 Spring Boot 的 DataSourceProperties.initializeDataSourceBuilder() 来创建数据源
     * 支持所有 Spring Boot 支持的数据源配置选项（如 HikariCP、Tomcat、DBCP2 等）
     *
     * @param dataSourceProperties Spring Boot 数据源配置属性
     * @param dataSourceName 数据源名称（用于日志）
     * @return 数据源实例
     */
    private DataSource createDataSource(DataSourceProperties dataSourceProperties, String dataSourceName) {
        if (dataSourceProperties.getUrl() == null || dataSourceProperties.getUrl().trim().isEmpty()) {
            throw new SystemException(dataSourceName + " URL 不能为空");
        }

        if (dataSourceProperties.getUsername() == null || dataSourceProperties.getUsername().trim().isEmpty()) {
            throw new SystemException(dataSourceName + " 用户名不能为空");
        }

        try {
            // 使用 Spring Boot 的 DataSourceProperties.initializeDataSourceBuilder() 方法
            // 这个方法会自动应用 DataSourceProperties 中的所有配置，包括：
            // - url, username, password, driverClassName
            // - type (数据源类型)
            // - 以及所有其他 Spring Boot 支持的数据源配置选项
            DataSourceBuilder<?> builder = dataSourceProperties.initializeDataSourceBuilder();

            DataSource dataSource = builder.build();
            LOGGER.debug("创建{}成功: {}", dataSourceName, dataSourceProperties.getUrl());
            return dataSource;

        } catch (Exception e) {
            throw new SystemException("创建" + dataSourceName + "失败: " + e.getMessage(), e);
        }
    }
}
