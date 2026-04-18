package io.github.loncra.framework.mybatis.interceptor.json.support;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.annotation.JsonCollectionGenericType;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.mybatis.handler.NameValueEnumTypeHandler;
import io.github.loncra.framework.mybatis.interceptor.json.AbstractJsonCollectionPostInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * jackson json 实现的 json 集合后续映射拦截器
 *
 * @author maurice.chen
 */
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class JacksonJsonCollectionPostInterceptor extends AbstractJsonCollectionPostInterceptor {

    @Override
    protected Object doMappingResult(
            Object result,
            Class<?> type,
            List<PropertyDescriptor> propertyDescriptors
    ) {
        for (PropertyDescriptor pd : propertyDescriptors) {
            Class<?> collectionClass = null;

            Field field = ReflectionUtils.findField(result.getClass(), pd.getName());
            if (Objects.nonNull(field)) {
                collectionClass = field.getType();
            }

            Method method = pd.getReadMethod();
            if (Objects.nonNull(method)) {
                collectionClass = pd.getReadMethod().getReturnType();
            }

            if (Objects.isNull(collectionClass)) {
                continue;
            }

            Object value = null;
            if (field != null) {
                field.setAccessible(true);
                value = ReflectionUtils.getField(field, result);
            }

            if (Objects.isNull(value)) {
                continue;
            }

            JsonCollectionGenericType ann = getJsonCollectionGenericType(pd, type);
            Class<?> targetClass = ann.value();

            Object newValue;

            if (ValueEnum.class.isAssignableFrom(targetClass) || NameEnum.class.isAssignableFrom(targetClass)) {
                Collection<?> collection = CastUtils.cast(value);
                Stream<Object> stream = collection
                        .stream()
                        .map(o -> getEnumValue(o, targetClass))
                        .filter(Objects::nonNull);
                if (List.class.equals(collectionClass)) {
                    newValue = stream.collect(Collectors.toList());
                }
                else if (Set.class.equals(collectionClass)) {
                    newValue = stream.collect(Collectors.toSet());
                }
                else {
                    throw new SystemException("找不到对应类型为 [" + collectionClass + "] 的集合处理方式");
                }
            }
            else {
                CollectionType collectionType = TypeFactory.createDefaultInstance()
                        .constructCollectionType(CastUtils.cast(collectionClass), targetClass);
                newValue = CastUtils.convertValue(value, collectionType);
            }

            if (Objects.nonNull(pd.getWriteMethod())) {
                ReflectionUtils.invokeMethod(pd.getWriteMethod(), result, newValue);
            }
            else {
                Field writeField = ReflectionUtils.findField(result.getClass(), pd.getName());
                if (Objects.nonNull(writeField)) {
                    writeField.setAccessible(true);
                    ReflectionUtils.setField(writeField, result, newValue);
                }
            }
        }
        return result;
    }

    /**
     * 获取枚举值
     *
     * @param o           对象值
     * @param targetClass 目标枚举类型
     *
     * @return 枚举值
     */
    private Object getEnumValue(
            Object o,
            Class<?> targetClass
    ) {
        Object result = NameValueEnumTypeHandler.getValue(o, targetClass);

        if (Objects.isNull(result)) {
            result = Enum.valueOf(CastUtils.cast(targetClass), o.toString());
        }

        return result;
    }
}
