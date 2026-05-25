package io.github.loncra.framework.security.audit;

/**
 * 审计请求快照的忽略项与 principal 解析配置；可嵌套在 {@link Auditable#ignoreProperties()} 与 {@link io.github.loncra.framework.spring.security.core.audit.OperationDataTrace#ignoreProperties()} 上。
 *
 * @author maurice.chen
 */
public @interface AuditProperties {

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
    String principal() default "";

    /**
     * 是否忽略请求头
     *
     * @return true 是，否则 false
     */
    boolean ignoreRequestHeader() default false;

    /**
     * 是否忽略请求参数
     *
     * @return true 是，否则 false
     */
    boolean ignoreRequestParameters() default false;

    /**
     * 是否忽略请求体数据
     *
     * @return true 是，否则 false
     */
    boolean ignoreRequestBody() default false;
}
