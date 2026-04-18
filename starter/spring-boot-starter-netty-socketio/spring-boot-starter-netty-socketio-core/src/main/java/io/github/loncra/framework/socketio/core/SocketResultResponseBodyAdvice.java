package io.github.loncra.framework.socketio.core;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.socketio.api.ReturnValueSocketResult;
import io.github.loncra.framework.socketio.api.SocketResult;
import io.github.loncra.framework.spring.web.config.SpringWebMvcProperties;
import io.github.loncra.framework.spring.web.result.RestResponseBodyAdvice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.MethodParameter;
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

                ReturnValueSocketResult<?> returnValueSocketResult = CastUtils.cast(result);

                returnValue = returnValueSocketResult.getReturnValue();
            }
        }

        return super.beforeBodyWrite(returnValue, returnType, selectedContentType, selectedConverterType, request, response);
    }

}
