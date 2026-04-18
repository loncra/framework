package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.DateUtils;
import io.github.loncra.framework.commons.id.number.NumberIdEntity;
import io.github.loncra.framework.mybatis.config.OperationDataTraceProperties;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;
import io.github.loncra.framework.mybatis.plus.audit.MybatisPlusOperationDataTraceResolver;
import io.github.loncra.framework.security.audit.IdAuditEvent;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import io.github.loncra.framework.spring.security.core.entity.SecurityPrincipalOperationDataTraceRecord;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

/**
 * 用户明细操作数据留痕仓库实现
 *
 * @author maurice.chen
 */
public class SecurityPrincipalOperationDataTraceResolver extends MybatisPlusOperationDataTraceResolver {

    private final ControllerAuditProperties controllerAuditProperties;

    public static final String OPERATION_DATA_TRACE_ATTR_NAME = "operationDataTrace";

    public SecurityPrincipalOperationDataTraceResolver(
            OperationDataTraceProperties operationDataTraceProperties,
            ControllerAuditProperties controllerAuditProperties
    ) {
        super(operationDataTraceProperties);
        this.controllerAuditProperties = controllerAuditProperties;
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
        Object trace = httpServletRequest.getAttribute(OPERATION_DATA_TRACE_ATTR_NAME);
        if (Objects.isNull(trace) || Boolean.FALSE.equals(trace)) {
            return null;
        }

        SecurityContext context = SecurityContextHolder.getContext();
        if (Objects.isNull(context.getAuthentication())) {
            return null;
        }

        Authentication authentication = context.getAuthentication();
        if (!authentication.isAuthenticated()) {
            return null;
        }

        if (!AuditAuthenticationToken.class.isAssignableFrom(context.getAuthentication().getClass())) {
            return null;
        }

        AuditAuthenticationToken authenticationToken = CastUtils.cast(context.getAuthentication());

        List<OperationDataTraceRecord> records = super.createOperationDataTraceRecord(
                mappedStatement,
                statement,
                parameter
        );

        List<OperationDataTraceRecord> result = new LinkedList<>();
        AuditEvent controllerAuditEvent = CastUtils.cast(httpServletRequest.getAttribute(controllerAuditProperties.getAuditEventAttrName()));

        for (OperationDataTraceRecord record : records.stream().filter(Objects::nonNull).toList()) {

            SecurityPrincipalOperationDataTraceRecord traceRecord = CastUtils.of(record, SecurityPrincipalOperationDataTraceRecord.class);
            if (Objects.isNull(traceRecord)) {
                continue;
            }

            if (Objects.nonNull(controllerAuditEvent)) {
                traceRecord.setControllerAuditType(controllerAuditEvent.getType());
            }

            traceRecord.setPrincipal(authenticationToken);
            traceRecord.setRemark(DateUtils.dateFormat(record.getCreationTime()) + StringUtils.SPACE + record.getType().getName());

            result.add(traceRecord);
        }

        return result;
    }

    @Override
    public AuditEvent createAuditEvent(OperationDataTraceRecord record) {
        AuditEvent event = super.createAuditEvent(record);
        if (!SecurityPrincipalOperationDataTraceRecord.class.isAssignableFrom(record.getClass())) {
            return event;
        }
        SecurityPrincipalOperationDataTraceRecord dataTraceRecord = CastUtils.cast(record);
        AuditAuthenticationToken authenticationToken = CastUtils.cast(dataTraceRecord.getPrincipal());

        Map<String, Object> dataTraceRecordMap = CastUtils.convertValue(dataTraceRecord, CastUtils.MAP_TYPE_REFERENCE);
        dataTraceRecordMap.remove(NumberIdEntity.CREATION_TIME_FIELD_NAME);
        dataTraceRecordMap.remove(IdAuditEvent.PRINCIPAL_FIELD_NAME);

        Map<String, Object> data = new LinkedHashMap<>();

        AuditAuthenticationSuccessDetails details = CastUtils.cast(authenticationToken.getDetails());

        data.put(AuditAuthenticationToken.DETAILS_KEY, details);
        data.put(OPERATION_DATA_TRACE_ATTR_NAME, dataTraceRecordMap);

        //syncControllerAuditEvent(data);

        SecurityPrincipalOperationDataTraceRecord newValue = CastUtils.of(dataTraceRecord, SecurityPrincipalOperationDataTraceRecord.class);
        newValue.setPrincipal(authenticationToken.getName());
        return createAuditEvent(newValue, data);
    }

    /*private void syncControllerAuditEvent(Map<String, Object> data) {
        Optional<HttpServletRequest> optional = SpringMvcUtils.getHttpServletRequest();

        if (optional.isEmpty()) {
            return ;
        }

        Object controllerAuditEvent = optional.get().getAttribute(controllerAuditProperties.getAuditEventAttrName());
        if (Objects.isNull(controllerAuditEvent)) {
            return ;
        }

        AuditEvent auditEvent = Casts.cast(controllerAuditEvent);

        Object header = auditEvent.getData().get(controllerAuditProperties.getHeaderKey());
        Object param = auditEvent.getData().get(controllerAuditProperties.getParamKey());
        Object body = SpringMvcUtils.getRequestAttribute(RequestBodyAttributeAdviceAdapter.REQUEST_BODY_ATTRIBUTE_NAME);

        data.put(controllerAuditProperties.getHeaderKey(), header);
        data.put(controllerAuditProperties.getParamKey(), param);
        data.put(controllerAuditProperties.getBodyKey(), body);
    }*/
}
