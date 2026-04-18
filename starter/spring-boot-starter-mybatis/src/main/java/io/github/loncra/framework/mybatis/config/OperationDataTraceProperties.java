package io.github.loncra.framework.mybatis.config;

import io.github.loncra.framework.commons.DateUtils;
import io.github.loncra.framework.security.StoragePositionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 操作日志追踪配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.mybatis.operation-data-trace")
public class OperationDataTraceProperties {

    /**
     * 默认审计前缀名称
     */
    public static final String DEFAULT_AUDIT_PREFIX_NAME = "OPERATION_DATA_AUDIT";

    /**
     * 审计前缀名称
     */
    private String auditPrefixName = DEFAULT_AUDIT_PREFIX_NAME;

    /**
     * 日志格式化内容
     */
    private String dateFormat = DateUtils.DEFAULT_DATE_TIME_FORMATTER_PATTERN;

    /**
     * 存储定位解析器
     */
    private StoragePositionProperties storagePosition;

    /**
     * 构造函数
     */
    public OperationDataTraceProperties() {
    }

    /**
     * 获取审计前缀名称
     *
     * @return 审计前缀名称
     */
    public String getAuditPrefixName() {
        return auditPrefixName;
    }

    /**
     * 设置审计前缀名称
     *
     * @param auditPrefixName 审计前缀名称
     */
    public void setAuditPrefixName(String auditPrefixName) {
        this.auditPrefixName = auditPrefixName;
    }

    /**
     * 获取日志格式化内容
     *
     * @return 日期格式字符串
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 设置日志格式化内容
     *
     * @param dateFormat 日期格式字符串
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 获取存储定位解析器
     *
     * @return 存储定位配置
     */
    public StoragePositionProperties getStoragePosition() {
        return storagePosition;
    }

    /**
     * 设置存储定位解析器
     *
     * @param storagePosition 存储定位配置
     */
    public void setStoragePosition(StoragePositionProperties storagePosition) {
        this.storagePosition = storagePosition;
    }
}
