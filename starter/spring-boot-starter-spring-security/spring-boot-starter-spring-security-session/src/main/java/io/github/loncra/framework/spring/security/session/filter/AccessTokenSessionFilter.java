package io.github.loncra.framework.spring.security.session.filter;

import io.github.loncra.framework.spring.security.session.token.SessionAccessTokenContextRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.session.web.http.SessionRepositoryFilter;

import java.io.IOException;

/**
 * 通过 accessToken 方法服务时候的 session 过滤器，目的是改类型的请求都不使用 spring session 存在到 redis 中。
 *
 * @author maurice.chen
 */
public class AccessTokenSessionFilter implements Filter {

    private final SessionAccessTokenContextRepository sessionAccessTokenContextRepository;

    public AccessTokenSessionFilter(SessionAccessTokenContextRepository sessionAccessTokenContextRepository) {
        this.sessionAccessTokenContextRepository = sessionAccessTokenContextRepository;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = sessionAccessTokenContextRepository.getAccessToken(httpRequest);
        if (StringUtils.isNotEmpty(token)) {
            request.setAttribute(SessionRepositoryFilter.class + SessionRepositoryFilter.ALREADY_FILTERED_SUFFIX, true);
        }
        filterChain.doFilter(request, response);
    }
}

