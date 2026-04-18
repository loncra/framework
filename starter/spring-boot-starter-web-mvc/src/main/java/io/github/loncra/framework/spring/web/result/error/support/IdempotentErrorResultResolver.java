package io.github.loncra.framework.spring.web.result.error.support;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.idempotent.exception.IdempotentException;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.springframework.http.HttpStatus;

/**
 * 幂等错误结果集解析器
 *
 * @author maurice.chen
 */
public class IdempotentErrorResultResolver implements ErrorResultResolver {

    /**
     * 判断是否支持该异常
     *
     * @param error 异常对象
     *
     * @return true 如果支持，否则 false
     */
    @Override
    public boolean isSupport(Throwable error) {
        return IdempotentException.class.isAssignableFrom(error.getClass());
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
        IdempotentException exception = CastUtils.cast(error);

        return RestResult.of(
                exception.getMessage(),
                HttpStatus.TOO_MANY_REQUESTS.value(),
                String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value())
        );
    }
}
