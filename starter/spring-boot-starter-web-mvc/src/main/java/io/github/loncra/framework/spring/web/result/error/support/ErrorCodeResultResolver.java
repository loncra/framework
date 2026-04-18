package io.github.loncra.framework.spring.web.result.error.support;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.StatusErrorCodeException;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.springframework.http.HttpStatus;

/**
 * 错误代码结果集解析器实现
 *
 * @author maurice.chen
 */
public class ErrorCodeResultResolver implements ErrorResultResolver {

    /**
     * 判断是否支持该异常
     *
     * @param error 异常对象
     *
     * @return true 如果支持，否则 false
     */
    @Override
    public boolean isSupport(Throwable error) {
        return ErrorCodeException.class.isAssignableFrom(error.getClass());
    }

    /**
     * 解析异常并返回 REST 结果
     *
     * @param error 异常对象
     *
     * @return REST 结果
     */
    @Override
    public RestResult<Object> resolve(Throwable error) {

        ErrorCodeException exception = CastUtils.cast(error, ErrorCodeException.class);

        RestResult<Object> result = new RestResult<>();

        result.setExecuteCode(exception.getErrorCode());
        result.setMessage(exception.getMessage());
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        if (StatusErrorCodeException.class.isAssignableFrom(error.getClass())) {
            StatusErrorCodeException statusException = CastUtils.cast(error, StatusErrorCodeException.class);

            result.setStatus(statusException.getStatus());
        }

        return result;
    }
}
