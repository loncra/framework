package io.github.loncra.framework.security.plugin;

import io.github.loncra.framework.commons.annotation.Metadata;

import java.lang.annotation.*;

/**
 * 插件信息注解
 *
 * @author maurice.chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Plugin {

    /**
     * 资源名称
     *
     * @return 名称
     */
    String name();

    /**
     * 权限信息
     *
     * @return 权限信息
     */
    String[] authority() default "";

    /**
     * 唯一识别
     *
     * @return 值
     */
    String id() default "";

    /**
     * 父类识别
     *
     * @return 值
     */
    String parent() default "";

    /**
     * 类型, 默认为 {@link PluginInfo#DEFAULT_TYPE_VALUE}
     *
     * @return 类型
     *
     */
    String type() default PluginInfo.DEFAULT_TYPE_VALUE;

    /**
     * 来源
     *
     * @return 来源
     *
     */
    String[] sources() default {};

    /**
     * 顺序值，默认为 0
     *
     * @return 顺序之
     */
    int sort() default 0;

    /**
     * 备注
     *
     * @return 备注
     */
    String remark() default "";

    /**
     * 元数据信息，扩展内容使用
     *
     * @return 元数据信息集合
     */
    Metadata[] metadata() default {};

}
