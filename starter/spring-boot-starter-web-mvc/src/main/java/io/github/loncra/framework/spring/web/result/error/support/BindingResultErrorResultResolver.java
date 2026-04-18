package io.github.loncra.framework.spring.web.result.error.support;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.web.result.error.ErrorResultResolver;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

/**
 * 参数绑定结果异常解析器实现
 *
 * @author maurice.chen
 */
public class BindingResultErrorResultResolver implements ErrorResultResolver {

    /**
     * 默认的绑定结果忽略字段列表
     */
    private static final List<String> DEFAULT_BINDING_RESULT_IGNORE_FIELD = Arrays.asList(
            "rejectedValue",
            "bindingFailure",
            "objectName",
            "source",
            "codes",
            "arguments"
    );

    /**
     * 判断是否支持该异常
     *
     * @param error 异常对象
     *
     * @return true 如果支持，否则 false
     */
    @Override
    public boolean isSupport(Throwable error) {
        BindingResult bindingResult = extractBindingResult(error);
        return Objects.nonNull(bindingResult) && bindingResult.hasErrors();
    }

    /**
     * 解析异常并返回 REST 结果
     *
     * @param error 异常对象
     *
     * @return REST 结果
     */
    @Override
    public RestResult<Object> resolve(Throwable error) {
        BindingResult bindingResult = extractBindingResult(error);

        List<FieldError> fieldErrorResult = new LinkedList<>();

        if (Objects.nonNull(bindingResult)) {
            fieldErrorResult = bindingResult
                    .getAllErrors()
                    .stream()
                    .filter(o -> FieldError.class.isAssignableFrom(o.getClass()))
                    .map(o -> (FieldError) o)
                    .toList();
        }


        List<Map<String, Object>> data = new LinkedList<>();

        for (FieldError fieldError : fieldErrorResult) {
            //noinspection unchecked
            Map<String, Object> map = CastUtils.convertValue(fieldError, Map.class);
            map.entrySet().removeIf(i -> DEFAULT_BINDING_RESULT_IGNORE_FIELD.contains(i.getKey()));
            data.add(map);
        }

        return RestResult.of(
                "参数验证不通过",
                HttpStatus.BAD_REQUEST.value(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                data
        );
    }

    /**
     * 从异常中提取 BindingResult
     *
     * @param error 异常对象
     *
     * @return BindingResult 实例，如果无法提取则返回 null
     */
    private BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return CastUtils.cast(error, BindingResult.class);
        }
        else if (error instanceof MethodArgumentNotValidException) {
            return CastUtils.cast(error, MethodArgumentNotValidException.class);
        }
        return null;
    }
}
