package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecordHook;
import io.github.loncra.framework.mybatis.plus.audit.MybatisPlusOperationDataTraceRepository;
import io.github.loncra.framework.spring.security.core.audit.creator.OperationDataTraceAuditEventInterceptor;
import io.github.loncra.framework.spring.security.core.entity.ControllerAuditEventMetadata;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.boot.actuate.audit.AuditEvent;

import java.time.Instant;
import java.util.*;

/**
 * 与安全控制器审计联动的操作数据留痕仓库：仅在 HTTP 请求上存在 {@link OperationDataTraceAuditEventInterceptor} 预置的 {@code AuditEvent} 时创建留痕；
 * {@code principal} 取自该控制器审计事件；{@link #createAuditEvent} 将 {@link ControllerAuditEventMetadata} 与 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata} 合并为同一 {@code AuditEvent.data}（键 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata#OPERATION_DATA_TRACE_DATA_FIELD}）；
 * {@code save} 后移除 request attribute，避免 {@link io.github.loncra.framework.spring.security.core.audit.ControllerAuditHandlerInterceptor} 重复发布。
 *
 * @author maurice.chen
 * @see OperationDataTraceAuditEventInterceptor
 * @see io.github.loncra.framework.spring.security.core.audit.OperationDataTrace
 */
public class SecurityPrincipalOperationDataTraceRepository extends MybatisPlusOperationDataTraceRepository {

    private final OperationDataTraceAuditEventInterceptor operationDataTraceAuditEventInterceptor;

    public SecurityPrincipalOperationDataTraceRepository(
            OperationDataTraceProperties operationDataTraceProperties,
            List<OperationDataTraceRecordHook> operationDataTraceRecordHooks,
            OperationDataTraceAuditEventInterceptor operationDataTraceAuditEventInterceptor
    ) {
        super(operationDataTraceProperties, operationDataTraceRecordHooks);
        this.operationDataTraceAuditEventInterceptor = operationDataTraceAuditEventInterceptor;
    }

    @Override
    public List<OperationDataTraceRecord> createOperationDataTraceRecord(
            MappedStatement mappedStatement,
            Statement statement,
            Object parameter
    ) throws Exception {

        Optional<HttpServletRequest> optional = SpringMvcUtils.getHttpServletRequest();

        if (optional.isEmpty()) {
            return null;
        }

        HttpServletRequest httpServletRequest = optional.get();

        AuditEvent controllerAuditEvent = CastUtils.cast(httpServletRequest.getAttribute(operationDataTraceAuditEventInterceptor.getAuditType()));
        if (Objects.isNull(controllerAuditEvent)) {
            return null;
        }

        List<OperationDataTraceRecord> records = super.createOperationDataTraceRecord(
                mappedStatement,
                statement,
                parameter
        );

        return records.stream()
                .peek(s -> s.setPrincipal(controllerAuditEvent.getPrincipal()))
                .toList();
    }

    @Override
    public AuditEvent createAuditEvent(OperationDataTraceRecord record, Map<String, Object> data) {
        Optional<HttpServletRequest> optional = SpringMvcUtils.getHttpServletRequest();

        if (optional.isEmpty()) {
            return super.createAuditEvent(record, data);
        }

        HttpServletRequest httpServletRequest = optional.get();

        AuditEvent controllerAuditEvent = CastUtils.cast(httpServletRequest.getAttribute(operationDataTraceAuditEventInterceptor.getAuditType()));
        if (Objects.isNull(controllerAuditEvent)) {
            return super.createAuditEvent(record, data);
        }

        Object metadata = controllerAuditEvent.getData().get(RestResult.DEFAULT_METADATA_NAME);
        if (Objects.nonNull(metadata)) {
            ControllerAuditEventMetadata controller = CastUtils.cast(metadata);
            controller.setEndTime(Instant.now());
            controller.setExecuteStatus(ExecuteStatus.Success);

            Object body = SpringMvcUtils.getRequestAttribute(RequestBodyAttributeAdviceAdapter.REQUEST_BODY_ATTRIBUTE_NAME);
            Boolean ignore = SpringMvcUtils.getRequestAttribute(OperationDataTraceAuditEventInterceptor.IGNORE_REQUEST_BODY_ATTR_NAME);
            if (Objects.nonNull(body) && !ignore) {
                controller.setBody(body);
            }
        }

        Map<String, Object> newData = new LinkedHashMap<>(controllerAuditEvent.getData());
        newData.put(OperationDataTraceMetadata.OPERATION_DATA_TRACE_DATA_FIELD, record.getData());

        return super.createAuditEvent(record, controllerAuditEvent.getType(), newData);
    }

    @Override
    public void saveOperationDataTraceRecord(List<OperationDataTraceRecord> records) throws Exception {
        super.saveOperationDataTraceRecord(records);

        Optional<HttpServletRequest> optional = SpringMvcUtils.getHttpServletRequest();

        if (optional.isEmpty()) {
            return ;
        }

        HttpServletRequest httpServletRequest = optional.get();
        httpServletRequest.removeAttribute(operationDataTraceAuditEventInterceptor.getAuditType());
    }
}
