package io.github.loncra.framework.fasc.stratey;

import io.github.loncra.framework.fasc.exception.ApiException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author Fadada
 * 2021/9/8 16:30:57
 */
public interface JsonStrategy {

    /**
     * 转成json串
     *
     * @param object 序列化对象
     *
     * @return json字符串
     *
     * @throws ApiException 异常
     */
    String toJson(Object object) throws ApiException;

    /**
     * 转成Java对象
     *
     * @param json              json串
     * @param parameterizedType 类型
     * @param <T>               泛型
     *
     * @return 泛型实例
     *
     * @throws ApiException 异常
     */
    <T> T toJavaBean(
            String json,
            ParameterizedType parameterizedType
    ) throws ApiException;

    /**
     * 转成Java对象
     *
     * @param json json串
     * @param clzz 类名
     * @param <T>  泛型
     *
     * @return 泛型实例
     *
     * @throws ApiException 异常
     */
    <T> T toJavaBean(
            String json,
            Class<T> clzz
    ) throws ApiException;

    /**
     * 转成列表
     *
     * @param json json串
     * @param clzz 类名
     * @param <T>  泛型
     *
     * @return 泛型实例
     *
     * @throws ApiException 异常
     */
    <T> List<T> toList(
            String json,
            Class<T> clzz
    ) throws ApiException;
}
