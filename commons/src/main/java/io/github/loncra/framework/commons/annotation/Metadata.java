package io.github.loncra.framework.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * 元数据信息 注解
 *
 * @author maurice.chen
 */
@Documented
@Repeatable(MetadataElements.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Metadata {
    /**
     * 键
     *
     * @return 键
     */
    String key();

    /**
     * 值
     *
     * @return 值
     */
    String value();
}
