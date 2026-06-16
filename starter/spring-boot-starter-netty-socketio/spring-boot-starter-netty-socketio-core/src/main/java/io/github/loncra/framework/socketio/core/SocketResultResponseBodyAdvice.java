package io.github.loncra.framework.socketio.core;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.socketio.api.ReturnValueSocketResult;
import io.github.loncra.framework.socketio.api.SocketResult;
import io.github.loncra.framework.spring.web.config.SpringWebMvcProperties;
import io.github.loncra.framework.spring.web.result.RestResponseBodyAdvice;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Objects;

/**
 * socket 结果集响应处理器
 *
 * @author maurice
 */
@ControllerAdvice
public class SocketResultResponseBodyAdvice extends RestResponseBodyAdvice {

    private final SocketServerManager socketServerManager;

    public SocketResultResponseBodyAdvice(
            SpringWebMvcProperties properties,
            SocketServerManager socketServerManager
    ) {
        super(properties);
        this.socketServerManager = socketServerManager;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {

        Object returnValue = body;

        if (Objects.nonNull(body) && SocketResult.class.isAssignableFrom(body.getClass())) {
            SocketResult result = CastUtils.cast(body);
            if (CollectionUtils.isNotEmpty(result.getMessages())) {
                result.getMessages().forEach(socketServerManager::sendMessage);
            }

            if (ReturnValueSocketResult.class.isAssignableFrom(result.getClass())) {

                ReturnValueSocketResult<Object> returnValueSocketResult = CastUtils.cast(result);

                returnValue = convertRestResult(returnValueSocketResult);
            }
        }

        return super.beforeBodyWrite(returnValue, returnType, selectedContentType, selectedConverterType, request, response);
    }

    private @NonNull RestResult<Object> convertRestResult(ReturnValueSocketResult<Object> returnValueSocketResult) {
        RestResult<Object> restResult = new RestResult<>();

        restResult.setMessage(Objects.toString(returnValueSocketResult.getMessage(), HttpStatus.OK.getReasonPhrase()));
        restResult.setExecuteCode(Objects.toString(returnValueSocketResult.getExecuteCode(), String.valueOf(HttpStatus.OK.value())));
        restResult.setStatus(Objects.requireNonNullElse(returnValueSocketResult.getStatus(), HttpStatus.OK.value()));
        restResult.setData(returnValueSocketResult.getReturnValue());

        return restResult;
    }

}
