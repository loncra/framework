package io.github.loncra.framework.mybatis.plus.annotation;

import java.lang.annotation.*;

/**
 * 解密属性结合的注解
 *
 * @author maurice.chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface Decrypts {

    /**
     * 解密注解属性数组
     *
     * @return 加密注解属性数组
     */
    DecryptProperties[] value();

}
