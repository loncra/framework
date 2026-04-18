package io.github.loncra.framework.mybatis.interceptor.json;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.annotation.JsonCollectionGenericType;
import io.github.loncra.framework.mybatis.interceptor.json.support.JacksonJsonCollectionPostInterceptor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象对象字段或属性的 json 集合类，用于实现在 orm 对象中，有引用范型集合的字段或属性时的特殊映射，
 * 如 Jackson Json，对于范型字段的映射返回一个 List Map 的，调用到字段或属性转型错误。
 * 查看{@link JacksonJsonCollectionPostInterceptor}
 *
 * @author maurice.chen
 */
public abstract class AbstractJsonCollectionPostInterceptor implements Interceptor {

    /**
     * 拦截查询方法
     *
     * @param invocation 方法调用
     *
     * @return 处理后的结果
     *
     * @throws Throwable 执行异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object result = invocation.proceed();

        MappedStatement mappedStatement = CastUtils.cast(invocation.getArgs()[0]);

        Map<Class<?>, List<PropertyDescriptor>> classPropertiesMap = new LinkedHashMap<>();

        for (ResultMap resultMap : mappedStatement.getResultMaps()) {
            List<PropertyDescriptor> propertyDescriptors = this.getJsonCollectionProperties(resultMap.getType());
            if (propertyDescriptors.isEmpty()) {
                continue;
            }
            classPropertiesMap.put(resultMap.getType(), propertyDescriptors);
        }

        return mappingCollectionProperty(result, classPropertiesMap);
    }

    private Object mappingCollectionProperty(
            Object result,
            Map<Class<?>, List<PropertyDescriptor>> map
    ) {
        if (!Collection.class.isAssignableFrom(result.getClass()) || map.isEmpty()) {
            return result;
        }
        Collection<?> collection = CastUtils.cast(result);

        Map<Class<?>, List<Object>> classMap = new LinkedHashMap<>();

        for (Object o : collection) {

            Optional<Class<?>> optional = map.keySet().stream().filter(t -> t.isAssignableFrom(o.getClass())).findFirst();
            if (optional.isEmpty()) {
                continue;
            }

            Class<?> key = optional.get();
            List<Object> list = classMap.computeIfAbsent(key, k -> new LinkedList<>());
            list.add(o);
        }

        List<Object> newResult = new LinkedList<>();
        for (Map.Entry<Class<?>, List<Object>> entry : classMap.entrySet()) {
            List<Object> mapValues = entry
                    .getValue()
                    .stream()
                    .map(v -> doMappingResult(v, entry.getKey(), map.get(entry.getKey())))
                    .filter(Objects::nonNull)
                    .toList();
            newResult.addAll(mapValues);
        }

        return newResult;
    }

    /**
     * 执行映射结果处理
     *
     * @param result              结果对象
     * @param type                类型
     * @param propertyDescriptors 属性描述符列表
     *
     * @return 处理后的结果
     */
    protected abstract Object doMappingResult(
            Object result,
            Class<?> type,
            List<PropertyDescriptor> propertyDescriptors
    );

    private List<PropertyDescriptor> getJsonCollectionProperties(Class<?> targetClass) {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(targetClass);
        return Arrays
                .stream(propertyDescriptors)
                .filter(p -> this.getJsonCollectionGenericType(p, targetClass) != null)
                .collect(Collectors.toList());
    }

    /**
     * 获取 JSON 集合泛型类型注解
     *
     * @param propertyDescriptor 属性描述符
     * @param targetClass        目标类
     *
     * @return JSON 集合泛型类型注解，如果不存在则返回 null
     */
    protected JsonCollectionGenericType getJsonCollectionGenericType(
            PropertyDescriptor propertyDescriptor,
            Class<?> targetClass
    ) {
        JsonCollectionGenericType result = null;
        Method method = propertyDescriptor.getReadMethod();
        if (Objects.nonNull(method)) {
            result = AnnotatedElementUtils.findMergedAnnotation(method, JsonCollectionGenericType.class);
        }

        if (result == null) {
            Field field = ReflectionUtils.findField(targetClass, propertyDescriptor.getName());
            if (Objects.nonNull(field)) {
                result = AnnotatedElementUtils.findMergedAnnotation(field, JsonCollectionGenericType.class);
            }
        }

        return result;
    }

}
