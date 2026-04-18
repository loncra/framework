package io.github.loncra.framework.spring.security.core.audit;

import java.lang.annotation.*;

/**
 * 数据操作留痕注解
 *
 * @author maurice.chen
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationDataTrace {

    /**
     * 目标名称
     *
     * @return 目标名称
     */
    String name();

    /**
     * 获取当事人值的属性名称
     *
     * <p>
     * 如：
     * request 提交的参数或头信息存在 token=test，要获取 token 做 principal 的话，principal 就等于 token
     * </p>
     *
     * @return 当事人值的属性名称
     */
    String principal();
}
