package io.github.loncra.framework.canal.annotation;

import java.lang.annotation.*;

/**
 * 可通知的行数据变更表，用于告知系统哪些表是可以发送行数据变更通知使用。
 *
 * @author maurice.chen
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotifiableTable {

    /**
     * 表名称
     *
     * @return 表名称
     */
    String value();

    /**
     * 备注信息
     *
     * @return 备注信息
     */
    String comment() default "";
}
