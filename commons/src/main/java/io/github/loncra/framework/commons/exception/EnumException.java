package io.github.loncra.framework.commons.exception;

/**
 * 枚举异常
 *
 * @author maurice.chen
 */
public class EnumException extends SystemException {
    /**
     * 枚举异常
     */
    public EnumException() {
        super();
    }

    /**
     * 枚举异常
     *
     * @param message 异常信息
     */
    public EnumException(String message) {
        super(message);
    }

    /**
     * 枚举异常
     *
     * @param message 异常信息
     * @param cause   异常类
     *
     * @since 1.4
     */
    public EnumException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }

    /**
     * 枚举异常
     *
     * @param cause 异常类
     *
     * @since 1.4
     */
    public EnumException(Throwable cause) {
        super(cause);
    }
}
