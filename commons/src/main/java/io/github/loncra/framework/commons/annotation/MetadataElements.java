package io.github.loncra.framework.commons.annotation;

import java.lang.annotation.*;

/**
 * 元数据注解数组
 *
 * @author maurice.chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface MetadataElements {
    /**
     * 元数据注解数组
     *
     * @return 元数据注解数据
     */
    Metadata[] value();
}
