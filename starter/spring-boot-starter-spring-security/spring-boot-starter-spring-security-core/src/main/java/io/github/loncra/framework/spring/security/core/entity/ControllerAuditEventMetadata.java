package io.github.loncra.framework.spring.security.core.entity;

import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.commons.id.metadata.IdNameMetadata;
import io.github.loncra.framework.security.plugin.PluginInfo;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * 控制器层审计元数据，写入 {@link org.springframework.boot.actuate.audit.AuditEvent#getData()} 的 {@link io.github.loncra.framework.commons.RestResult#DEFAULT_METADATA_NAME} 键下。
 * <p>由 {@link io.github.loncra.framework.spring.security.core.audit.creator.AbstractAuditEventInterceptor} 在 {@code preHandle} 填充请求快照，
 * 在 {@code afterCompletion} 补全 {@link #endTime}、{@link #executeStatus} 等；与 DB 留痕合并时 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata} 以 sibling 键 {@code operationTrace} 写入同一 {@code data} 树。</p>
 *
 * @author maurice.chen
 */
public class ControllerAuditEventMetadata extends IdNameMetadata implements BasicIdentification<String> {

    private Instant startTime = Instant.now();

    private PluginInfo pluginInfo;

    private ExecuteStatus executeStatus = ExecuteStatus.Processing;

    private String exception;

    private Instant endTime;

    private String remark;

    private String url;

    private String httpMethod;

    private HttpHeaders headers;

    private Map<String, String[]> parameters;

    private Map<String, Object> body;

    public ControllerAuditEventMetadata() {
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    public ExecuteStatus getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(ExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Duration getDuration() {
        if (startTime == null || endTime == null) {
            return null;
        }
        return Duration.between(startTime, endTime);
    }
}
