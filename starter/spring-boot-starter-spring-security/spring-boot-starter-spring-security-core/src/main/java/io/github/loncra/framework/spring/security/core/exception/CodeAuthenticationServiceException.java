package io.github.loncra.framework.spring.security.core.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

import java.io.Serial;

/**
 * 带有错误代码的服务认证异常
 *
 * @author maurice.chen
 */
public class CodeAuthenticationServiceException extends InternalAuthenticationServiceException {

    @Serial
    private static final long serialVersionUID = 2020884863621640533L;

    private final String code;

    public CodeAuthenticationServiceException(
            String msg,
            String code
    ) {
        super(msg);
        this.code = code;
    }

    public CodeAuthenticationServiceException(
            String msg,
            Throwable cause,
            String code
    ) {
        super(msg, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
