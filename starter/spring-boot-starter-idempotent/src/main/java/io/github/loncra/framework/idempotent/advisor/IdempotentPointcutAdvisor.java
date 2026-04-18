package io.github.loncra.framework.idempotent.advisor;

import io.github.loncra.framework.idempotent.annotation.Idempotent;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.io.Serial;

/**
 * aop 的幂等性切面处理实现
 *
 * @author maurice
 */
public class IdempotentPointcutAdvisor extends AbstractPointcutAdvisor {

    @Serial
    private static final long serialVersionUID = -2973618152809395856L;

    /**
     * 幂等拦截器
     */
    private final IdempotentInterceptor idempotentInterceptor;

    /**
     * 构造函数
     *
     * @param idempotentInterceptor 幂等拦截器
     */
    public IdempotentPointcutAdvisor(IdempotentInterceptor idempotentInterceptor) {
        this.idempotentInterceptor = idempotentInterceptor;
    }

    @Override
    public Pointcut getPointcut() {
        return new AnnotationMatchingPointcut(null, Idempotent.class, true);
    }

    @Override
    public Advice getAdvice() {
        return idempotentInterceptor;
    }
}
