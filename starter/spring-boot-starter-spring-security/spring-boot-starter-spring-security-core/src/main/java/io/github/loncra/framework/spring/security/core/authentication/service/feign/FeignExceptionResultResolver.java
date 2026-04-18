package io.github.loncra.framework.spring.security.core.authentication.service.feign;

import feign.FeignException;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * feign 异常结果集解析器实现
 *
 * @author maurice.chen
 */
public class FeignExceptionResultResolver implements ErrorResultResolver {

    @Override
    public boolean isSupport(Throwable error) {
        return FeignException.class.isAssignableFrom(error.getClass());
    }

    @Override
    public RestResult<Object> resolve(Throwable error) {
        FeignException exception = CastUtils.cast(error);

        RestResult<Object> result = RestResult.of(
                "调用远程服务错误",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())
        );

        HttpStatus status = HttpStatus.resolve(exception.status());

        if (Objects.nonNull(status)) {
            result.setStatus(status.value());
            result.setExecuteCode(String.valueOf(status.value()));
        }

        return result;
    }
}
