package io.github.loncra.framework.spring.security.core.audit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 控制器审计配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.controller.audit")
public class ControllerAuditProperties {

    /**
     * 请求体缓存默认上限（字节）
     */
    public static final int DEFAULT_CACHED_BODY_MAX_BYTES = 1024 * 1024;

    /**
     * {@link io.github.loncra.framework.spring.security.core.audit.CachedBodyFilter} 默认排序值
     */
    public static final int DEFAULT_CACHED_BODY_FILTER_ORDER = org.springframework.core.Ordered.HIGHEST_PRECEDENCE + 5;

    public static final String DEFAULT_AUDIT_NAME = "controllerAudit";

    public static final String DEFAULT_OPERATION_DATA_TRACE_AUDIT_NAME = "operationDataTraceAudit";

    /**
     * 控制器审计名称
     */
    private String controllerAuditName = DEFAULT_AUDIT_NAME;

    /**
     * 操作数据审计名称
     */
    private String operationDataTraceAuditName = DEFAULT_OPERATION_DATA_TRACE_AUDIT_NAME;

    /**
     * 是否启用请求体缓存 Filter（须在 HandlerInterceptor 之前执行）
     */
    private boolean enabledCachedBodyFilter = true;

    /**
     * 请求体缓存 Filter 排序值
     */
    private int cachedBodyFilterOrder = DEFAULT_CACHED_BODY_FILTER_ORDER;

    /**
     * 请求体缓存最大字节数，超出则跳过缓存
     */
    private int cachedBodyMaxBytes = DEFAULT_CACHED_BODY_MAX_BYTES;

    public ControllerAuditProperties() {
    }

    public String getControllerAuditName() {
        return controllerAuditName;
    }

    public void setControllerAuditName(String controllerAuditName) {
        this.controllerAuditName = controllerAuditName;
    }

    public String getOperationDataTraceAuditName() {
        return operationDataTraceAuditName;
    }

    public void setOperationDataTraceAuditName(String operationDataTraceAuditName) {
        this.operationDataTraceAuditName = operationDataTraceAuditName;
    }

    public boolean isEnabledCachedBodyFilter() {
        return enabledCachedBodyFilter;
    }

    public void setEnabledCachedBodyFilter(boolean enabledCachedBodyFilter) {
        this.enabledCachedBodyFilter = enabledCachedBodyFilter;
    }

    public int getCachedBodyFilterOrder() {
        return cachedBodyFilterOrder;
    }

    public void setCachedBodyFilterOrder(int cachedBodyFilterOrder) {
        this.cachedBodyFilterOrder = cachedBodyFilterOrder;
    }

    public int getCachedBodyMaxBytes() {
        return cachedBodyMaxBytes;
    }

    public void setCachedBodyMaxBytes(int cachedBodyMaxBytes) {
        this.cachedBodyMaxBytes = cachedBodyMaxBytes;
    }

}
