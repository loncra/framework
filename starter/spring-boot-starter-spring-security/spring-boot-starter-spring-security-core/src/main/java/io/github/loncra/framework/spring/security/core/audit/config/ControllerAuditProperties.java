package io.github.loncra.framework.spring.security.core.audit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 控制器审计配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.controller.audit")
public class ControllerAuditProperties {


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

}
