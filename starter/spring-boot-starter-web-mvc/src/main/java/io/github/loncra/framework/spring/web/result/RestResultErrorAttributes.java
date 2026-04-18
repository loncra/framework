package io.github.loncra.framework.spring.web.result;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.ServiceException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

/**
 * rest 格式的全局错误实现
 *
 * @author maurice.chen
 */
public class RestResultErrorAttributes extends DefaultErrorAttributes {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResultErrorAttributes.class);

    /**
     * 默认的错误执行属性名称
     */
    public static final String DEFAULT_ERROR_EXECUTE_ATTR_NAME = "REST_ERROR_ATTRIBUTES_EXECUTE";

    /**
     * 默认支持抛出消息的异常类型集合
     */
    public static final List<Class<? extends Exception>> DEFAULT_MESSAGE_EXCEPTION = Arrays.asList(
            IllegalArgumentException.class,
            ServiceException.class,
            SystemException.class
    );

    /**
     * 默认支持消息的 HTTP 状态码集合
     */
    public static final List<HttpStatus> DEFAULT_HTTP_STATUSES_MESSAGE = Arrays.asList(
            HttpStatus.FORBIDDEN,
            HttpStatus.UNAUTHORIZED
    );

    /**
     * 错误结果解析器列表
     */
    private final List<ErrorResultResolver> resultResolvers;

    /**
     * 支持的异常抛出消息的类
     */
    private final List<Class<? extends Exception>> supportException;

    /**
     * 支持的 http 响应状态
     */
    private final List<HttpStatus> supportHttpStatus;

    /**
     * 创建一个 rest 格式的全局错误实现
     *
     * @param resultResolvers   错误结果解析器列表
     * @param supportException  支持的异常抛出消息的类
     * @param supportHttpStatus 支持的 http 响应状态
     */
    public RestResultErrorAttributes(
            List<ErrorResultResolver> resultResolvers,
            List<Class<? extends Exception>> supportException,
            List<HttpStatus> supportHttpStatus
    ) {
        this.resultResolvers = resultResolvers;
        this.supportException = supportException;
        this.supportHttpStatus = supportHttpStatus;
    }

    /**
     * 获取错误属性
     *
     * @param webRequest Web 请求
     * @param options    错误属性选项
     *
     * @return 错误属性映射
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest,
            ErrorAttributeOptions options
    ) {

        HttpStatus status = getStatus(webRequest);

        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        RestResult<Object> result = RestResult.of(
                ErrorCodeException.DEFAULT_ERROR_MESSAGE,
                status.value(),
                ErrorCodeException.DEFAULT_EXCEPTION_CODE,
                new LinkedHashMap<>()
        );

        if (supportHttpStatus.contains(status)) {
            result.setMessage(status.getReasonPhrase());
        }

        Throwable error = getError(webRequest);

        if (Objects.nonNull(error)) {
            Optional<ErrorResultResolver> optional = resultResolvers
                    .stream()
                    .filter(r -> r.isSupport(error))
                    .findFirst();

            if (optional.isPresent()) {
                result = optional.get().resolve(error);
            }
            else if (supportException.stream().anyMatch(e -> e.isAssignableFrom(error.getClass()))) {
                result.setMessage(error.getMessage());
            }
            LOGGER.error("服务器异常", error);
        }
        else {
            LOGGER.error("服务器异常:{}", result);
        }

        webRequest.setAttribute(DEFAULT_ERROR_EXECUTE_ATTR_NAME, true, RequestAttributes.SCOPE_REQUEST);

        return CastUtils.convertValue(result, Map.class);
    }

    /**
     * 获取 http 状态
     *
     * @param webRequest web 请求
     *
     * @return http 状态
     */
    private HttpStatus getStatus(WebRequest webRequest) {

        Integer status = CastUtils.cast(webRequest.getAttribute(
                "jakarta.servlet.error.status_code",
                RequestAttributes.SCOPE_REQUEST
        ));

        if (status == null) {
            return null;
        }

        try {
            return HttpStatus.valueOf(status);
        }
        catch (Exception e) {
            return null;
        }
    }
}
