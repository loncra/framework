package io.github.loncra.framework.spring.security.core.audit.creator;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.MetadataUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.generator.SpringExpressionMetadataGenerator;
import io.github.loncra.framework.security.audit.AuditProperties;
import io.github.loncra.framework.security.audit.IdAuditEvent;
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.security.plugin.PluginInfo;
import io.github.loncra.framework.spring.security.core.audit.AuditEventInterceptor;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationSuccessDetails;
import io.github.loncra.framework.spring.security.core.entity.ControllerAuditEventMetadata;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.method.HandlerMethod;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link AuditEventInterceptor} 模板实现：从 {@link AuditProperties} 采集请求快照、解析 principal、
 * 构建 {@link ControllerAuditEventMetadata}，并通过 {@link SpringExpressionMetadataGenerator} 解析 {@link Metadata} SpEL。
 * <p>{@link #createControllerMetadata} 在 {@code preHandle} 采集 URL/头/参数，<b>不</b>读取请求体（早于 {@code @RequestBody} 绑定）；
 * body 由 {@link RequestBodyAttributeAdviceAdapter} 写入 attribute 后，由子类或留痕仓库在更晚阶段填充。</p>
 * <p>{@link #afterCompletion}：HTTP 200 时 {@link ExecuteStatus#Success}；非 200 时 {@link ExecuteStatus#Failure}，
 * 有 {@code ex} 记录 {@code ex.getMessage()}，否则记录 {@link HttpStatus} reason phrase。</p>
 *
 * @author maurice.chen
 */
public abstract class AbstractAuditEventInterceptor implements AuditEventInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuditEventInterceptor.class);

    private final ControllerAuditProperties controllerAuditProperties;

    public AbstractAuditEventInterceptor(
            ControllerAuditProperties controllerAuditProperties
    ) {
        this.controllerAuditProperties = controllerAuditProperties;
    }

    @Override
    public AuditEvent preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    ) {
        AuditProperties auditProperties = getControllerHandlerMethodAuditProperties(request, response, handlerMethod);
        if (Objects.isNull(auditProperties)) {
            return null;
        }
        ControllerAuditEventMetadata metadata = createControllerMetadata(auditProperties, request);
        metadata.setId(handlerMethod.getMethod().getName());
        if (StringUtils.isEmpty(metadata.getName())) {
            metadata.setName(metadata.getId());
        }
        Object principal = getPrincipal(auditProperties.principal(), request);

        Metadata[] metadataValue = postControllerAuditEventMetadata(metadata, request, response, handlerMethod);
        //CastUtils.convertValue(metadata,CastUtils.MAP_TYPE_REFERENCE);
        SpringExpressionMetadataGenerator generator = new SpringExpressionMetadataGenerator(
                Map.of(
                        RestResult.DEFAULT_METADATA_NAME, metadata,
                        IdAuditEvent.PRINCIPAL_FIELD_NAME, principal
                )
        );

        metadata.getMetadata().putAll(MetadataUtils.toMap(metadataValue, generator));

        Map<String, Object> data = new HashMap<>();
        data.put(RestResult.DEFAULT_METADATA_NAME, metadata);
        String principalName = principal.toString();
        if (AuditAuthenticationToken.class.isAssignableFrom(principal.getClass())) {
            AuditAuthenticationToken authenticationToken = CastUtils.cast(principal);
            AuditAuthenticationSuccessDetails temp = getAuditAuthenticationSuccessDetails(request, authenticationToken);
            data.put(AuditAuthenticationToken.DETAILS_KEY, temp);
            principalName = authenticationToken.getName();
        }

        AuditEvent event = new AuditEvent(Instant.now(), principalName, getAuditType(), data);

        Plugin plugin = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Plugin.class);
        if (Objects.isNull(plugin)) {
            return event;
        }

        metadata.setPluginInfo(new PluginInfo(plugin));

        Plugin root = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Plugin.class);
        if (StringUtils.isEmpty(metadata.getName())) {
            metadata.setName(plugin.name());
            if (Objects.nonNull(root)) {
                metadata.setName(root.name() + CastUtils.UNDERSCORE + plugin.name());
            }
        }

        if (ArrayUtils.isNotEmpty(plugin.metadata())) {
            metadata.getMetadata().putAll(MetadataUtils.toMap(plugin.metadata(), generator));
            if (Objects.nonNull(root) && ArrayUtils.isNotEmpty(root.metadata())) {
                metadata.getMetadata().putAll(MetadataUtils.toMap(root.metadata(), generator));
            }
        }

        if (StringUtils.isEmpty(metadata.getRemark())) {
            metadata.setRemark(plugin.remark());
        }

        return new AuditEvent(Instant.now(), principalName, getAuditType(), data);
    }

    protected abstract Metadata[] postControllerAuditEventMetadata(
            ControllerAuditEventMetadata metadata,
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    );

    protected abstract AuditProperties getControllerHandlerMethodAuditProperties(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handlerMethod
    );

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            HandlerMethod handler,
            Exception ex,
            AuditEvent auditEvent
    ) {
        try {

            ControllerAuditEventMetadata controllerAuditEventMetadata = CastUtils.cast(auditEvent.getData().get(RestResult.DEFAULT_METADATA_NAME));
            controllerAuditEventMetadata.setEndTime(Instant.now());

            if (HttpStatus.OK.value() == response.getStatus()) {
                controllerAuditEventMetadata.setExecuteStatus(ExecuteStatus.Success);
            } 
            else {
                controllerAuditEventMetadata.setExecuteStatus(ExecuteStatus.Failure);
                if (Objects.nonNull(ex)) {
                    controllerAuditEventMetadata.setException(ex.getMessage());
                } else {
                    controllerAuditEventMetadata.setException(HttpStatus.valueOf(response.getStatus()).getReasonPhrase());
                }
            }
        } catch (Exception e) {
            LOGGER.warn("执行 AbstractAuditEventInterceptor.afterCompletion 内容时出现异常", e);
        }

    }

    protected AuditAuthenticationSuccessDetails getAuditAuthenticationSuccessDetails(
            HttpServletRequest request,
            AuditAuthenticationToken authenticationToken
    ) {
        AuditAuthenticationSuccessDetails successDetails = CastUtils.cast(authenticationToken.getDetails());
        return new AuditAuthenticationSuccessDetails(
                new WebAuthenticationDetails(request),
                successDetails.getMetadata()
        );
    }

    protected Object getPrincipal(
            String key,
            HttpServletRequest request
    ) {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (Objects.isNull(securityContext.getAuthentication()) || !securityContext.getAuthentication().isAuthenticated()) {
            String principal = null;

            if (StringUtils.isNotBlank(key)) {
                principal = request.getParameter(key);
            }
            if (StringUtils.isBlank(principal)) {
                principal = request.getHeader(key);
            }
            if (StringUtils.isBlank(principal)) {
                principal = request.getRemoteAddr();
            }

            return principal;
        }
        else {
            return securityContext.getAuthentication();
        }
    }

    public ControllerAuditEventMetadata createControllerMetadata(
            AuditProperties auditProperties,
            HttpServletRequest request
    ) {
        ControllerAuditEventMetadata controllerAuditEventMetadata = new ControllerAuditEventMetadata();
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        controllerAuditEventMetadata.setUrl(servletServerHttpRequest.getURI().toString());
        controllerAuditEventMetadata.setHttpMethod(servletServerHttpRequest.getMethod().toString());

        if (!auditProperties.ignoreRequestHeader()) {
            controllerAuditEventMetadata.setHeaders(servletServerHttpRequest.getHeaders());
        }

        if (MapUtils.isNotEmpty(request.getParameterMap()) && !auditProperties.ignoreRequestParameters()) {
            controllerAuditEventMetadata.setParameters(request.getParameterMap());
        }

        return controllerAuditEventMetadata;
    }

    public ControllerAuditProperties getControllerAuditProperties() {
        return controllerAuditProperties;
    }
}
