package io.github.loncra.framework.mybatis.plus.annotation;

import io.github.loncra.framework.mybatis.plus.CryptoNullClass;
import io.github.loncra.framework.mybatis.plus.EncryptService;

import java.lang.annotation.*;

/**
 * 加密，用于在需要加密字段内容时使用
 *
 * @author maurice.chen
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encryption {

    /**
     * spring bean 名称，如果该值存在，先去找 beanName 的实例，找不到该实例后再去找 {@link #serviceClass()} 实例
     *
     * @return spring bean 名称
     */
    String beanName() default "";

    /**
     * 加密服务
     *
     * @return 加密服务
     */
    Class<? extends EncryptService> serviceClass() default CryptoNullClass.class;
}
