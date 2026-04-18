package io.github.loncra.framework.datasource.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 读写分离数据源配置属性
 * <p>
 * 使用 Spring Boot 的 DataSourceProperties 来配置数据源，支持所有 Spring Boot 支持的数据源配置选项
 * <p>
 * 配置示例：
 * <pre>
 * loncra:
 *   framework:
 *      datasource:
 *        enabled: true
 *        master:
 *          url: jdbc:mysql://localhost:3306/master
 *          username: root
 *          password: password
 *          driver-class-name: com.mysql.cj.jdbc.Driver
 *          type: com.zaxxer.hikari.HikariDataSource
 *          hikari:
 *            maximum-pool-size: 10
 *        slaves:
 *          - url: jdbc:mysql://localhost:3306/slave1
 *            username: root
 *            password: password
 *            driver-class-name: com.mysql.cj.jdbc.Driver
 *          - url: jdbc:mysql://localhost:3306/slave2
 *            username: root
 *            password: password
 *            driver-class-name: com.mysql.cj.jdbc.Driver
 * </pre>
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.datasource")
public class LoncraDataSourceProperties {

    /**
     * 是否启用读写分离数据源
     */
    private boolean enabled = true;

    /**
     * 主数据源配置（使用 Spring Boot 的 DataSourceProperties）
     */
    private DataSourceProperties master;

    /**
     * 从数据源配置列表（使用 Spring Boot 的 DataSourceProperties）
     */
    private List<DataSourceProperties> slaves = new ArrayList<>();

    /**
     * 默认构造函数
     */
    public LoncraDataSourceProperties() {
    }

    /**
     * 获取是否启用读写分离数据源
     *
     * @return 是否启用
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置是否启用读写分离数据源
     *
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取主数据源配置
     *
     * @return 主数据源配置
     */
    public DataSourceProperties getMaster() {
        return master;
    }

    /**
     * 设置主数据源配置
     *
     * @param master 主数据源配置
     */
    public void setMaster(DataSourceProperties master) {
        this.master = master;
    }

    /**
     * 获取从数据源配置列表
     *
     * @return 从数据源配置列表
     */
    public List<DataSourceProperties> getSlaves() {
        return slaves;
    }

    /**
     * 设置从数据源配置列表
     *
     * @param slaves 从数据源配置列表
     */
    public void setSlaves(List<DataSourceProperties> slaves) {
        this.slaves = slaves != null ? slaves : new ArrayList<>();
    }
}
