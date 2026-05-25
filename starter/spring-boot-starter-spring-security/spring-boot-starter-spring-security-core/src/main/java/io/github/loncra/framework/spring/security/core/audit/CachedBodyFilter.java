package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.spring.security.core.audit.config.ControllerAuditProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 在 {@link ControllerAuditHandlerInterceptor#preHandle} 之前缓存请求体，
 * 并写入 {@link #REQUEST_BODY_ATTRIBUTE_NAME}，供 {@link io.github.loncra.framework.spring.security.core.audit.creator.AbstractAuditEventInterceptor#createControllerMetadata} 等使用；
 * 通过 {@link CachedBodyHttpServletRequestWrapper} 保证下游 {@code @RequestBody} 仍可重复读取。
 * <p>由 {@link io.github.loncra.framework.spring.security.core.SpringSecurityAutoConfiguration#cachedBodyFilterRegistration} 注册；
 * 配置见 {@link ControllerAuditProperties}（{@code enabled-cached-body-filter}、{@code cached-body-max-bytes}）。</p>
 *
 * @author maurice.chen
 * @see CachedBodyHttpServletRequestWrapper
 */
public class CachedBodyFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedBodyFilter.class);

    public static final String REQUEST_BODY_ATTRIBUTE_NAME = CachedBodyFilter.class.getName();

    private final int maxBodyBytes;

    public CachedBodyFilter() {
        this(ControllerAuditProperties.DEFAULT_CACHED_BODY_MAX_BYTES);
    }

    public CachedBodyFilter(int maxBodyBytes) {
        this.maxBodyBytes = maxBodyBytes;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (!shouldCacheBody(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        CachedBodyHttpServletRequestWrapper wrappedRequest = new CachedBodyHttpServletRequestWrapper(request);
        if (wrappedRequest.getCachedBody().length > maxBodyBytes) {
            LOGGER.debug("请求体超过缓存上限 {} 字节，跳过 body 缓存: {}", maxBodyBytes, request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        Object body = resolveRequestBody(wrappedRequest);
        if (body != null) {
            wrappedRequest.setAttribute(REQUEST_BODY_ATTRIBUTE_NAME, body);
        }
        filterChain.doFilter(wrappedRequest, response);
    }

    static boolean shouldCacheBody(HttpServletRequest request) {
        String method = request.getMethod();
        if (!HttpMethod.POST.matches(method)
                && !HttpMethod.PUT.matches(method)
                && !HttpMethod.PATCH.matches(method)) {
            return false;
        }
        String contentType = StringUtils.defaultString(request.getContentType());
        if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            return false;
        }
        return request.getContentLength() != 0;
    }

    static Object resolveRequestBody(CachedBodyHttpServletRequestWrapper request) {
        if (!request.hasBody()) {
            return null;
        }

        String contentType = StringUtils.defaultString(request.getContentType());
        if (contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.isEmpty()) {
                return null;
            }
            return parameterMap;
        }

        Charset charset = resolveCharset(request);
        String raw = new String(request.getCachedBody(), charset);
        if (StringUtils.isBlank(raw)) {
            return null;
        }

        if (contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                return CastUtils.getObjectMapper().readValue(raw, CastUtils.MAP_TYPE_REFERENCE);
            }
            catch (Exception e) {
                LOGGER.debug("JSON 请求体解析为 Map 失败，按原始字符串缓存", e);
                return Map.of("_raw", raw);
            }
        }

        return Map.of("_raw", raw);
    }

    private static Charset resolveCharset(HttpServletRequest request) {
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            return StandardCharsets.UTF_8;
        }
        try {
            return Charset.forName(encoding);
        }
        catch (Exception ignored) {
            return StandardCharsets.UTF_8;
        }
    }
}
