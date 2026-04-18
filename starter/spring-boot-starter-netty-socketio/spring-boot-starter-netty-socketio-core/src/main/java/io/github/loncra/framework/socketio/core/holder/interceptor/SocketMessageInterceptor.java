package io.github.loncra.framework.socketio.core.holder.interceptor;

import io.github.loncra.framework.socketio.api.SocketResult;
import io.github.loncra.framework.socketio.core.SocketServerManager;
import io.github.loncra.framework.socketio.core.holder.SocketResultHolder;
import io.github.loncra.framework.socketio.core.holder.annotation.SocketMessage;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Objects;

/**
 * `@SocketMessage` 方法拦截器。
 * <p>
 * 在目标方法执行后，从 {@link SocketResultHolder} 读取待发送消息并统一下发，
 * 最后清理线程上下文，避免数据污染到后续请求。
 *
 * @author maurice.chen
 */
public class SocketMessageInterceptor implements MethodInterceptor {

    private final SocketServerManager socketServerManager;

    public SocketMessageInterceptor(SocketServerManager socketServerManager) {
        this.socketServerManager = socketServerManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        SocketMessage message = AnnotationUtils.findAnnotation(invocation.getMethod(), SocketMessage.class);

        if (Objects.isNull(message)) {
            return invocation.proceed();
        }

        try {

            Object returnValue = invocation.proceed();

            SocketResult socketResult = SocketResultHolder.get();
            if (CollectionUtils.isNotEmpty(socketResult.getMessages())) {
                socketResult.getMessages().forEach(socketServerManager::sendMessage);
            }

            return returnValue;
        }
        finally {
            SocketResultHolder.clear();
        }
    }
}
