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
     * 是否审计，开启时会将本次请求的所有请求头和参数以及请求体数据存储到审计内容中
     *
     * @return true 是，否则 false
     */
    boolean audit() default false;

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
     * 是否操作数据留痕，开启时如果涉及到数据库操作的修改将会保存修改的数据到审计内容中
     *
     * @return true 是，否则 false
     */
    boolean operationDataTrace() default false;

    /**
     * 元数据信息，扩展内容使用
     *
     * @return 元数据信息集合
     */
    Metadata[] metadata() default {};

}
