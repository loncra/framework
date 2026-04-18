package io.github.loncra.framework.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.loncra.framework.commons.annotation.GetValueStrategy;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.exception.EnumException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class EnumUtils {

    /**
     * 获取枚举类中需要忽略的字段列表（通过 @JsonIgnore 或 @JsonIgnoreProperties 注解标记）
     *
     * @param enumClass 枚举类
     *
     * @return 需要忽略的字段名称列表
     */
    public static Set<String> getJsonIgnoreList(Class<?> enumClass) {

        Set<String> ignoreList = new HashSet<>();

        JsonIgnoreProperties jsonIgnoreProperties = AnnotationUtils.findAnnotation(enumClass, JsonIgnoreProperties.class);

        if (jsonIgnoreProperties != null) {
            ignoreList.addAll(Arrays.asList(jsonIgnoreProperties.value()));
        }

        Field[] fieldList = enumClass.getFields();

        for (Field field : fieldList) {
            JsonIgnore jsonIgnore = AnnotationUtils.findAnnotation(field, JsonIgnore.class);
            if (jsonIgnore != null) {
                ignoreList.add(field.getName());
            }
        }

        return ignoreList;
    }

    public static <E extends Enum<?>> E[] checkThenReturnEnum(Class<E> enumClass) {
        if (enumClass == null) {
            throw new EnumException("枚举类不能为 null");
        }
        if (!enumClass.isEnum()) {
            throw new EnumException(enumClass.getName() + " 并非枚举类型，无法根据 name 查找");
        }

        E[] constants = enumClass.getEnumConstants();
        if (constants == null || constants.length == 0) {
            throw new EnumException(enumClass.getName() + " 中没有枚举项");
        }

        return constants;
    }


    /**
     * 根据 @GetValueStrategy 注解策略获取枚举的值
     *
     * @param valueEnum ValueEnum 实例
     *
     * @return 根据策略返回的值（可能是 getValue()、getName() 或 name()）
     */
    public static Object getValueByStrategyAnnotation(ValueEnum<?> valueEnum) {
        GetValueStrategy getValueStrategy = AnnotatedElementUtils.findMergedAnnotation(valueEnum.getClass(), GetValueStrategy.class);
        if (Objects.isNull(getValueStrategy)) {
            return valueEnum.getValue();
        }

        GetValueStrategy.Type type = getValueStrategy.type();

        if (GetValueStrategy.Type.Value.equals(type)) {
            return valueEnum.getValue();
        }

        Enum<? extends ValueEnum<?>> e = CastUtils.cast(valueEnum);
        return e.name();
    }

    /**
     * 根据 @GetValueStrategy 注解策略获取枚举的值
     *
     * @param nameEnum NameEnum 实例
     *
     * @return 根据策略返回的值（可能是 getValue()、getName() 或 name()）
     */
    public static Object getValueByStrategyAnnotation(NameEnum nameEnum) {
        GetValueStrategy getValueStrategy = AnnotatedElementUtils.findMergedAnnotation(nameEnum.getClass(), GetValueStrategy.class);
        if (Objects.isNull(getValueStrategy)) {
            return nameEnum.getName();
        }

        GetValueStrategy.Type type = getValueStrategy.type();

         if (GetValueStrategy.Type.Name.equals(type)) {
            return nameEnum.getName();
        }

        Enum<? extends NameEnum> e = CastUtils.cast(nameEnum);
        return e.name();
    }

    /**
     * 根据 @GetValueStrategy 注解策略获取枚举的值
     *
     * @param nameValueEnum NameValueEnum 实例
     *
     * @return 根据策略返回的值（可能是 getValue()、getName() 或 name()）
     */
    public static Object getValueByStrategyAnnotation(NameValueEnum<?>  nameValueEnum) {
        GetValueStrategy getValueStrategy = AnnotatedElementUtils.findMergedAnnotation(nameValueEnum.getClass(), GetValueStrategy.class);
        if (Objects.isNull(getValueStrategy)) {
            return nameValueEnum.getValue();
        }
        GetValueStrategy.Type type = getValueStrategy.type();

        if (GetValueStrategy.Type.Value.equals(type)) {
            return nameValueEnum.getValue();
        }
        else if (GetValueStrategy.Type.Name.equals(type)) {
            return nameValueEnum.getName();
        }

        Enum<? extends NameValueEnum<?>> e = CastUtils.cast(nameValueEnum);
        return e.name();
    }

}
