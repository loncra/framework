package io.github.loncra.framework.spring.security.core.authentication;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * json 形式的 session 超时策略实现
 *
 * @author maurice
 */
public class JsonSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletResponse response = event.getResponse();

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        RestResult<Map<String, Object>> result = RestResult.of(
                httpStatus.getReasonPhrase(),
                httpStatus.value(),
                ErrorCodeException.DEFAULT_EXCEPTION_CODE,
                new LinkedHashMap<>()
        );

        response.getWriter().write(CastUtils.getObjectMapper().writeValueAsString(result));

    }
}

