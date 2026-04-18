package io.github.loncra.framework.spring.security.core.authentication.handler;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.security.filter.result.IgnoreOrDesensitizeResultHolder;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 响应 JSON 数据的认证成功处理实现
 *
 * @author maurice.chen
 */
public class JsonAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * 认证成功响应列表
     */
    private final List<JsonAuthenticationSuccessResponse> successResponses;

    /**
     * 登录请求匹配器列表
     */
    private final List<PathPatternRequestMatcher> loginRequestMatchers = new LinkedList<>();

    /**
     * 构造函数
     *
     * @param successResponses         认证成功响应列表
     * @param authenticationProperties 认证配置属性
     */
    public JsonAuthenticationSuccessHandler(
            List<JsonAuthenticationSuccessResponse> successResponses,
            AuthenticationProperties authenticationProperties
    ) {
        this(successResponses, authenticationProperties, new LinkedList<>());
    }

    /**
     * 构造函数
     *
     * @param successResponses         认证成功响应列表
     * @param authenticationProperties 认证配置属性
     * @param antPathRequestMatchers   路径模式请求匹配器列表
     */
    public JsonAuthenticationSuccessHandler(
            List<JsonAuthenticationSuccessResponse> successResponses,
            AuthenticationProperties authenticationProperties,
            List<PathPatternRequestMatcher> antPathRequestMatchers
    ) {

        this.successResponses = successResponses;

        if (CollectionUtils.isNotEmpty(antPathRequestMatchers)) {
            this.loginRequestMatchers.addAll(antPathRequestMatchers);
        }

        this.loginRequestMatchers.add(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, authenticationProperties.getLoginProcessingUrl()));
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException, ServletException {

        if (loginRequestMatchers.stream().noneMatch(matcher -> matcher.matches(request))) {
            chain.doFilter(request, response);
        }
        else {
            onAuthenticationSuccess(request, response, authentication);
        }
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        RestResult<Object> result = RestResult.of(HttpStatus.OK.getReasonPhrase());
        result.setData(authentication);

        if (CollectionUtils.isNotEmpty(successResponses)) {
            successResponses.forEach(f -> f.setting(result, request));
        }

        if (loginRequestMatchers.stream().anyMatch(matcher -> matcher.matches(request))) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Object data = IgnoreOrDesensitizeResultHolder.convert(result);
            response.getWriter().write(CastUtils.getObjectMapper().writeValueAsString(data));
        }
    }
}
