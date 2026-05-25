package io.github.loncra.framework.spring.security.core.audit;

import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.security.audit.AuditProperties;

import java.lang.annotation.*;

/**
 * 标注在控制器方法上，开启<b>写库留痕</b>链路：{@link io.github.loncra.framework.spring.security.core.audit.creator.OperationDataTraceAuditEventInterceptor} 预置 {@code AuditEvent}，
 * MyBatis {@code OperationDataTraceInterceptor} 经 {@link SecurityPrincipalOperationDataTraceRepository} 合并 {@link io.github.loncra.framework.mybatis.domain.metadata.OperationDataTraceMetadata} 后发布。
 * <p>未标注本注解时，即使存在 SQL 写操作也<b>不会</b>生成 DB 留痕审计事件。</p>
 * <p>请求忽略项见嵌套 {@link #ignoreProperties()}；{@link #principal()} 与 {@link AuditProperties#principal()} 语义一致（亦可仅通过 {@code ignoreProperties} 配置）。</p>
 *
 * @author maurice.chen
 * @see io.github.loncra.framework.spring.security.core.audit.creator.OperationDataTraceAuditEventInterceptor
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
    String name() default "";

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
     * 备注
     *
     * @return 备注信息
     */
    String remark() default "";

    /**
     * 元数据信息，扩展内容使用
     *
     * @return 元数据信息集合
     */
    Metadata[] metadata() default {};

    /**
     * 审计忽略
     *
     * @return 忽略配置
     */
    AuditProperties ignoreProperties() default @AuditProperties;
}
