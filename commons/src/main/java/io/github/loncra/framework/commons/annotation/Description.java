package io.github.loncra.framework.commons.annotation;


import java.lang.annotation.*;

/**
 * 描述注解
 *
 * @author maurice.chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
public @interface Description {

    /**
     * 描述信息
     *
     * @return 描述内容
     */
    String value();

    /**
     * 名称
     *
     * @return 名称
     */
    String name() default "";

    /**
     * 顺序值
     *
     * @return 顺序值
     */
    int sort() default Integer.MAX_VALUE;
}
