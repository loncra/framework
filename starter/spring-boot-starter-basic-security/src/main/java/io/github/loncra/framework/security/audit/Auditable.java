package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.commons.annotation.Metadata;

import java.lang.annotation.*;

/**
 * 需审计的内容注解
 *
 * @author maurice
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

    /**
     * 审计名称
     *
     * @return 名称
     */
    String name() default "";

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
