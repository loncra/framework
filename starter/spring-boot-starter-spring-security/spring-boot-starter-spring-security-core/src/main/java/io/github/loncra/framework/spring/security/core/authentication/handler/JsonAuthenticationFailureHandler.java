package io.github.loncra.framework.spring.security.core.authentication.handler;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.exception.CodeAuthenticationServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 响应 json 数据的认证失败处理实现
 *
 * @author maurice.chen
 */
public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final List<JsonAuthenticationFailureResponse> failureResponses;

    private final List<PathPatternRequestMatcher> loginRequestMatchers = new LinkedList<>();

    public JsonAuthenticationFailureHandler(
            List<JsonAuthenticationFailureResponse> failureResponses,
            AuthenticationProperties authenticationProperties
    ) {
        this(failureResponses, authenticationProperties, new LinkedList<>());
    }

    public JsonAuthenticationFailureHandler(
            List<JsonAuthenticationFailureResponse> failureResponses,
            AuthenticationProperties authenticationProperties,
            List<PathPatternRequestMatcher> antPathRequestMatchers
    ) {

        this.failureResponses = failureResponses;

        if (CollectionUtils.isNotEmpty(antPathRequestMatchers)) {
            this.loginRequestMatchers.addAll(antPathRequestMatchers);
        }

        this.loginRequestMatchers.add(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, authenticationProperties.getLoginProcessingUrl()));
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e
    ) throws IOException {

        if (loginRequestMatchers.stream().noneMatch(matcher -> matcher.matches(request))) {
            return;
        }

        RestResult<Map<String, Object>> result = RestResult.ofException(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e,
                new LinkedHashMap<>()
        );
        if (e instanceof CodeAuthenticationServiceException errorCode) {
            result.setExecuteCode(errorCode.getCode());
        }

        if (CollectionUtils.isNotEmpty(failureResponses)) {
            failureResponses.forEach(f -> f.setting(result, request, e));
        }

        response.setStatus(result.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(CastUtils.getObjectMapper().writeValueAsString(result));
    }

}
