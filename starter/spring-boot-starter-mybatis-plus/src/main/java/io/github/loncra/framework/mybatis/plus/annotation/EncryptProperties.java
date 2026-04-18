package io.github.loncra.framework.mybatis.plus.annotation;

import io.github.loncra.framework.mybatis.plus.CryptoNullClass;
import io.github.loncra.framework.mybatis.plus.EncryptService;

import java.lang.annotation.*;

/**
 * 加密属性配置
 *
 * @author maurice.chen
 */
@Documented
@Repeatable(Encrypts.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface EncryptProperties {

    /**
     * 需要加密的字段名称数组
     *
     * @return 字段名称数组
     */
    String[] value();

    /**
     * Spring Bean 名称，如果该值存在，先去找 beanName 的实例，找不到该实例后再去找 {@link #serviceClass()} 实例
     *
     * @return Spring Bean 名称
     */
    String beanName() default "";

    /**
     * 加密服务类
     *
     * @return 加密服务类
     */
    Class<? extends EncryptService> serviceClass() default CryptoNullClass.class;
}
