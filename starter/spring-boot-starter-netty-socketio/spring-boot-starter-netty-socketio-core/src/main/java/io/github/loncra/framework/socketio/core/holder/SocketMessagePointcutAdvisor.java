package io.github.loncra.framework.socketio.core.holder;

import io.github.loncra.framework.socketio.core.holder.annotation.SocketMessage;
import io.github.loncra.framework.socketio.core.holder.interceptor.SocketMessageInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serial;
import java.lang.reflect.Method;

/**
 * socket 消息注解的切面实现
 *
 * @author maurice
 */
public class SocketMessagePointcutAdvisor extends AbstractPointcutAdvisor {

    @Serial
    private static final long serialVersionUID = 2352948490042970291L;

    private final SocketMessageInterceptor socketMessageInterceptor;

    public SocketMessagePointcutAdvisor(SocketMessageInterceptor socketMessageInterceptor) {
        this.socketMessageInterceptor = socketMessageInterceptor;
    }

    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(
                    Method method,
                    Class<?> targetClass
            ) {
                return method.isAnnotationPresent(SocketMessage.class) || targetClass.isAnnotationPresent(SocketMessage.class);
            }

        };
    }

    @Override
    public Advice getAdvice() {
        return socketMessageInterceptor;
    }
}
