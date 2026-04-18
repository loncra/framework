package io.github.loncra.framework.commons.exception;


import java.io.Serial;

/**
 * 业务逻辑异常实现.
 *
 * @author maurice.chen
 **/
public class ServiceException extends SystemException {

    @Serial
    private static final long serialVersionUID = 5031974444998025805L;

    /**
     * 业务逻辑异常实现
     */
    public ServiceException() {
    }

    /**
     * 业务逻辑异常实现
     *
     * @param message 异常信息
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 业务逻辑异常实现
     *
     * @param message 错误信息
     * @param cause   异常类
     */
    public ServiceException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }

    /**
     * 业务逻辑异常实现
     *
     * @param cause 异常类
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
