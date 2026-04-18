package io.github.loncra.framework.mybatis.plus.interceptor;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.NamingUtils;
import io.github.loncra.framework.mybatis.plus.annotation.LastModifiedDate;
import io.github.loncra.framework.mybatis.plus.service.BasicService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 最后更新时间内部拦截器
 *
 * @author maurice.chen
 */
public class LastModifiedDateInnerInterceptor implements InnerInterceptor {

    /**
     * 是否使用下划线命名（snake_case）
     */
    private boolean snakeCase = true;

    /**
     * 是否支持包装器模式
     */
    private boolean wrapperMode = false;

    /**
     * 构造函数
     */
    public LastModifiedDateInnerInterceptor() {
    }

    /**
     * 构造函数
     *
     * @param wrapperMode 是否支持包装器模式
     */
    public LastModifiedDateInnerInterceptor(boolean wrapperMode) {
        this.wrapperMode = wrapperMode;
    }

    /**
     * 构造函数
     *
     * @param wrapperMode 是否支持包装器模式
     * @param snakeCase   是否使用下划线命名（snake_case）
     */
    public LastModifiedDateInnerInterceptor(
            boolean wrapperMode,
            boolean snakeCase
    ) {
        this.wrapperMode = wrapperMode;
        this.snakeCase = snakeCase;
    }

    /**
     * 在更新操作之前设置最后修改时间
     *
     * @param executor  执行器
     * @param ms        MappedStatement
     * @param parameter 参数对象
     */
    @Override
    public void beforeUpdate(
            Executor executor,
            MappedStatement ms,
            Object parameter
    ) {
        if (SqlCommandType.UPDATE != ms.getSqlCommandType()) {
            return;
        }
        if (parameter instanceof Map) {
            Map<String, Object> map = CastUtils.cast(parameter);
            doSetLastModifiedDate(map, ms.getId());
        }
    }

    /**
     * 设置最后修改时间
     *
     * @param map  参数映射
     * @param msId MappedStatement ID
     */
    private void doSetLastModifiedDate(
            Map<String, Object> map,
            String msId
    ) {
        // updateById(et), update(et, wrapper);
        Object et = map.getOrDefault(Constants.ENTITY, null);
        if (Objects.nonNull(et)) {
            // LastModifiedDate field
            List<Field> fields = this.getLastModifiedDateField(et.getClass());
            if (CollectionUtils.isEmpty(fields)) {
                return;
            }

            fields.forEach(field -> this.setLastModifiedDateValue(et, field));
        }
        else if (wrapperMode && map.entrySet().stream().anyMatch(t -> Objects.equals(t.getKey(), Constants.WRAPPER))) {
            // update(LambdaUpdateWrapper) or update(UpdateWrapper)
            this.setLastModifiedDateByWrapper(map, msId);
        }
    }

    /**
     * 通过包装器设置最后修改时间
     *
     * @param map  参数映射
     * @param msId MappedStatement ID
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setLastModifiedDateByWrapper(
            Map<String, Object> map,
            String msId
    ) {
        Object ew = map.get(Constants.WRAPPER);
        if (Objects.isNull(ew) || !AbstractWrapper.class.isAssignableFrom(ew.getClass())) {
            return;
        }

        if (!Update.class.isAssignableFrom(ew.getClass())) {
            return;
        }

        Update updateWrapper = CastUtils.cast(ew);
        Class<?> entityClass = BasicService.getEntityClass(msId);

        List<Field> fields = this.getLastModifiedDateField(entityClass);
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }

        if (snakeCase) {
            fields.forEach(f -> updateWrapper.set(NamingUtils.castCamelCaseToSnakeCase(f.getName()), getDateValue(f)));
        }
        else {
            fields.forEach(f -> updateWrapper.set(f.getName(), getDateValue(f)));
        }
    }

    /**
     * 设置最后修改时间字段值
     *
     * @param target 目标对象
     * @param field  字段
     */
    private void setLastModifiedDateValue(
            Object target,
            Field field
    ) {
        Object value = getDateValue(field);
        field.setAccessible(true);
        ReflectionUtils.setField(field, target, value);
    }

    /**
     * 根据字段类型获取日期值
     *
     * @param field 字段
     *
     * @return 日期值，支持 Date、LocalDateTime、LocalDate、LocalTime、Long、Instant 类型
     */
    public Object getDateValue(Field field) {
        Class<?> fieldType = field.getType();
        if (Date.class.isAssignableFrom(fieldType)) {
            return new Date();
        }
        else if (LocalDateTime.class.isAssignableFrom(fieldType)) {
            return LocalDateTime.now();
        }
        else if (LocalDate.class.isAssignableFrom(fieldType)) {
            return LocalDate.now();
        }
        else if (LocalTime.class.isAssignableFrom(fieldType)) {
            return LocalTime.now();
        }
        else if (Long.class.isAssignableFrom(fieldType)) {
            return System.currentTimeMillis();
        }
        else if (Instant.class.isAssignableFrom(fieldType)) {
            return Instant.now();
        }
        return null;
    }

    /**
     * 获取标记了 {@link LastModifiedDate} 注解的字段列表
     *
     * @param aClass 类
     *
     * @return 字段列表
     */
    private List<Field> getLastModifiedDateField(Class<?> aClass) {
        List<Field> fields = new LinkedList<>();
        ReflectionUtils.doWithFields(aClass, fields::add);
        return fields.stream().filter(f -> Objects.nonNull(AnnotatedElementUtils.findMergedAnnotation(f, LastModifiedDate.class))).collect(Collectors.toList());
    }
}
