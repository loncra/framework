package io.github.loncra.framework.idempotent.exception;

import io.github.loncra.framework.commons.exception.SystemException;

import java.io.Serial;


/**
 * 幂等性异常
 *
 * @author maurice.chen
 */
public class IdempotentException extends SystemException {

    @Serial
    private static final long serialVersionUID = -8218863087525865969L;

    /**
     * 构造函数
     */
    public IdempotentException() {
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public IdempotentException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     * @param cause   原因
     */
    public IdempotentException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }

    /**
     * 构造函数
     *
     * @param cause 原因
     */
    public IdempotentException(Throwable cause) {
        super(cause);
    }
}
