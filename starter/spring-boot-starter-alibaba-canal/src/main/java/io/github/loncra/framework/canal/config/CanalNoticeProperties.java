package io.github.loncra.framework.canal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 通知配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.canal.notice")
public class CanalNoticeProperties {

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 需要扫描的包路径
     */
    private List<String> basePackages;

    /**
     * 构造函数
     */
    public CanalNoticeProperties() {
    }

    /**
     * 获取数据库名称
     *
     * @return 数据库名称
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * 设置数据库名称
     *
     * @param databaseName 数据库名称
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * 获取需要扫描的包路径
     *
     * @return 需要扫描的包路径
     */
    public List<String> getBasePackages() {
        return basePackages;
    }

    /**
     * 设置需要扫描的包路径
     *
     * @param basePackages 需要扫描的包路径
     */
    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }
}
