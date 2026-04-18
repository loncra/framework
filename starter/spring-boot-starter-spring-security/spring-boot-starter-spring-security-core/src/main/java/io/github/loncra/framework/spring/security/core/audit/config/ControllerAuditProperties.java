package io.github.loncra.framework.spring.security.core.audit.config;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 控制器审计配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.controller.audit")
public class ControllerAuditProperties {

    public static final String DEFAULT_AUDIT_EVENT_ATTR_NAME = "controllerAuditEvent";

    public static final String DEFAULT_SUCCESS_SUFFIX_NAME = "SUCCESS";

    public static final String DEFAULT_FAILURE_SUFFIX_NAME = "FAILURE";

    public static final String DEFAULT_EXCEPTION_KEY_NAME = "exception";

    public static final String DEFAULT_AUDIT_PREFIX_NAME = "CONTROLLER_AUDIT";

    public static final String DEFAULT_HEADER_KEY = "header";

    public static final String DEFAULT_PARAM_KEY = "parameter";

    public static final String DEFAULT_BODY_KEY = "body";

    public static final String DEFAULT_EXECUTION_END_TIME = "executionEndTime";

    /**
     * 成功执行的后缀名称，用与说明执行某个动作时区分成功或失败或异常
     */
    private String successSuffixName = DEFAULT_SUCCESS_SUFFIX_NAME;

    /**
     * 失败执行的后缀名称，用与说明执行某个动作时区分成功或失败或异常
     */
    private String failureSuffixName = DEFAULT_FAILURE_SUFFIX_NAME;

    /**
     * 异常执行的后缀名称，用与说明执行某个动作时区分成功或失败或异常
     */
    private String exceptionKeyName = DEFAULT_EXCEPTION_KEY_NAME;

    /**
     * 审计前缀
     */
    private String auditPrefixName = DEFAULT_AUDIT_PREFIX_NAME;

    /**
     * 请求头 key 名称，用于就请求头的信息写入  {@link AuditEvent#getData()} 中使用
     */
    private String headerKey = DEFAULT_HEADER_KEY;

    /**
     * 请求参数 key 名称，用于就请求参数的信息写入  {@link AuditEvent#getData()}
     */
    private String paramKey = DEFAULT_PARAM_KEY;

    /**
     * 请求体 key 名称，用于就请求体的信息写入  {@link AuditEvent#getData()}
     */
    private String bodyKey = DEFAULT_BODY_KEY;

    /**
     * 审计事件存储在 request 属性中的名称
     */
    private String auditEventAttrName = DEFAULT_AUDIT_EVENT_ATTR_NAME;

    /**
     * 执行时间
     */
    private String executionEndTimeKey = DEFAULT_EXECUTION_END_TIME;

    public ControllerAuditProperties() {
    }

    public String getSuccessSuffixName() {
        return successSuffixName;
    }

    public void setSuccessSuffixName(String successSuffixName) {
        this.successSuffixName = successSuffixName;
    }

    public String getFailureSuffixName() {
        return failureSuffixName;
    }

    public void setFailureSuffixName(String failureSuffixName) {
        this.failureSuffixName = failureSuffixName;
    }

    public String getExceptionKeyName() {
        return exceptionKeyName;
    }

    public void setExceptionKeyName(String exceptionKeyName) {
        this.exceptionKeyName = exceptionKeyName;
    }

    public String getAuditPrefixName() {
        return auditPrefixName;
    }

    public void setAuditPrefixName(String auditPrefixName) {
        this.auditPrefixName = auditPrefixName;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getBodyKey() {
        return bodyKey;
    }

    public void setBodyKey(String bodyKey) {
        this.bodyKey = bodyKey;
    }

    public String getAuditEventAttrName() {
        return auditEventAttrName;
    }

    public void setAuditEventAttrName(String auditEventAttrName) {
        this.auditEventAttrName = auditEventAttrName;
    }

    public String getExecutionEndTimeKey() {
        return executionEndTimeKey;
    }

    public void setExecutionEndTimeKey(String executionEndTimeKey) {
        this.executionEndTimeKey = executionEndTimeKey;
    }
}
