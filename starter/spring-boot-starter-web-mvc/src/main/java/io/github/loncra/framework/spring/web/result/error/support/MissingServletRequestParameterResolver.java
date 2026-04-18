package io.github.loncra.framework.spring.web.result.error.support;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 没有指定参数错误解析器
 *
 * @author maurice.chen
 */
public class MissingServletRequestParameterResolver implements ErrorResultResolver {

    /**
     * 默认的对象名称
     */
    public final static String DEFAULT_OBJECT_NAME = "ServletRequestParameter";

    /**
     * 对象名称
     */
    private final String objectName;

    /**
     * 创建一个没有指定参数错误解析器
     *
     * @param objectName 对象名称
     */
    public MissingServletRequestParameterResolver(String objectName) {
        this.objectName = objectName;
    }

    /**
     * 判断是否支持该异常
     *
     * @param error 异常对象
     *
     * @return true 如果支持，否则 false
     */
    @Override
    public boolean isSupport(Throwable error) {

        return MissingServletRequestParameterException.class.isAssignableFrom(error.getClass());
    }

    /**
     * 解析异常并返回 REST 结果
     *
     * @param error 异常对象
     *
     * @return REST 结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public RestResult<Object> resolve(Throwable error) {
        MissingServletRequestParameterException exception = CastUtils.cast(error);
        FieldError fieldError = new FieldError(objectName, exception.getParameterName(), error.getMessage());
        List<Map<String, Object>> data = new LinkedList<>();
        data.add(CastUtils.convertValue(fieldError, Map.class));

        return RestResult.of(
                "参数丢失",
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                data
        );
    }
}
