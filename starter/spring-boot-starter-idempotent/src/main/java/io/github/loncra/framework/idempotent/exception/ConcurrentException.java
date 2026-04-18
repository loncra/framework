package io.github.loncra.framework.idempotent.exception;


import java.io.Serial;

/**
 * 并发异常
 *
 * @author maurice
 */
public class ConcurrentException extends IdempotentException {

    @Serial
    private static final long serialVersionUID = 2181395755678626994L;

    /**
     * 构造函数
     */
    public ConcurrentException() {
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public ConcurrentException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     * @param cause   原因
     */
    public ConcurrentException(
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
    public ConcurrentException(Throwable cause) {
        super(cause);
    }
}
