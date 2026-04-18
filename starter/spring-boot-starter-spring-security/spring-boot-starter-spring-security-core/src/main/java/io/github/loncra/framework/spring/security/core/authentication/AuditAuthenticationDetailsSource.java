package io.github.loncra.framework.spring.security.core.authentication;

import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.entity.AuditAuthenticationDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.Objects;

/**
 * AuditAuthenticationToken 的认证明细元实现
 *
 * @author maurice.chen
 */
public class AuditAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

    private final AuthenticationProperties authenticationProperties;

    public AuditAuthenticationDetailsSource(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(context);
        WebAuthenticationDetails webAuthenticationDetails = super.buildDetails(context);
        String type = context.getParameter(authenticationProperties.getTypeParamName());
        if (StringUtils.isEmpty(type)) {
            type = Objects.toString(context.getHeader(authenticationProperties.getTypeHeaderName()), StringUtils.EMPTY);
        }
        return new AuditAuthenticationDetails(
                webAuthenticationDetails,
                type,
                HttpRequestParameterMapUtils.castMapToMultiValueMap(context.getParameterMap()),
                servletServerHttpRequest.getHeaders()
        );
    }
}
