package io.github.loncra.framework.commons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.loncra.framework.commons.exception.SystemException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.objenesis.instantiator.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 转型工具类
 * <p>
 * 专注于类型转换和对象转型操作
 *
 * @author maurice.chen
 */
public abstract class CastUtils {


    /**
     * 点符号
     */
    public static final String DOT = ".";

    /**
     * 下划线符号
     */
    public static final String UNDERSCORE = "_";

    /**
     * 负号/减号
     */
    public final static String NEGATIVE = "-";

    /**
     * 分号
     */
    public final static String SEMICOLON = ";";

    /**
     * 逗号
     */
    public final static String COMMA = ",";

    /**
     * map 类型范型引用
     */
    public static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<>() {};

    /**
     * map 参数类型引用
     */
    public static final ParameterizedTypeReference<Map<String, Object>> MAP_PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {};

    /**
     * list map 类型范型引用
     */
    public static final TypeReference<List<Map<String, Object>>> LIST_MAP_TYPE_REFERENCE = new TypeReference<>() {};

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 设置 jackson objectMapper
     *
     * @param objectMapper objectMapper
     */
    public static void setObjectMapper(ObjectMapper objectMapper) {
        CastUtils.objectMapper = objectMapper;
    }

    /**
     * 将值转换成指定类型的对象
     *
     * @param value 值
     * @param type  指定类型
     * @param <T>   对象范型实体值
     *
     * @return 指定类型的对象实例
     */
    public static <T> T convertValue(
            Object value,
            Class<T> type
    ) {
        return objectMapper.convertValue(value, type);
    }

    /**
     * 将值转换成指定类型的对象
     *
     * @param value       值
     * @param toValueType 指定类型
     * @param <T>         对象范型实体值
     *
     * @return 指定类型的对象实例
     */
    public static <T> T convertValue(
            Object value,
            JavaType toValueType
    ) {
        return objectMapper.convertValue(value, toValueType);
    }

    /**
     * 将值转换成指定类型的对象
     *
     * @param value 值
     * @param type  引用类型
     * @param <T>   对象范型实体值
     *
     * @return 指定类型的对象实例
     */
    public static <T> T convertValue(
            Object value,
            TypeReference<T> type
    ) {
        return objectMapper.convertValue(value, type);
    }

    /**
     * 获取 object mapper
     *
     * @return object mapper
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 将 value 转型为返回值类型
     *
     * @param value 值
     * @param <T>   值类型
     *
     * @return 转型后的值
     */
    public static <T> T cast(Object value) {
        if (value == null) {
            return null;
        }
        return (T) cast(value, value.getClass());
    }

    /**
     * 如果 value 不为 null 值，将 value 转型为返回值类型
     *
     * @param value 值
     * @param <T>   值类型
     *
     * @return 转型后的值
     */
    public static <T> T castIfNotNull(Object value) {
        if (value == null) {
            return null;
        }
        return cast(value);
    }

    /**
     * 将 value 转型为返回值类型
     *
     * @param value 值
     * @param type  值类型 class
     * @param <T>   值类型
     *
     * @return 转型后的值
     */
    public static <T> T cast(
            Object value,
            Class<T> type
    ) {
        return (T) (value == null ? null : ConvertUtils.convert(value, type));
    }

    /**
     * 将值转型为 Optional 类型
     *
     * @param value 值
     * @param <T>   值类型
     *
     * @return Optional
     */
    public static <T> Optional<T> castOptional(Object value) {
        return Optional.ofNullable((T) value);
    }

    /**
     * 如果 value 不为 null 值，将 value 转型为返回值类型
     *
     * @param value 值
     * @param type  值类型 class
     * @param <T>   值类型
     *
     * @return 转型后的值
     */
    public static <T> T castIfNotNull(
            Object value,
            Class<T> type
    ) {
        if (value == null) {
            return null;
        }
        return cast(value, type);
    }

    /**
     * 创建一个新的对象，并将 source 属性内容拷贝到创建的对象中
     *
     * @param source           原数据
     * @param targetClass      新的对象类型
     * @param ignoreProperties 要忽略的属性名称
     *
     * @return 新的对象内容
     */
    public static <T> T of(
            Object source,
            Class<T> targetClass,
            String... ignoreProperties
    ) {
        T result = null;

        if (Objects.nonNull(targetClass)) {
            result = ClassUtils.newInstance(targetClass);
        }

        if (Objects.nonNull(source)) {
            BeanUtils.copyProperties(source, result, ignoreProperties);
        }

        return result;
    }

    /**
     * 将 Map 对象转换为指定类型的对象
     *
     * @param source           源 Map 对象
     * @param targetClass      目标类型
     * @param ignoreProperties 要忽略的属性名称
     * @param <T>              目标类型
     *
     * @return 转换后的对象
     */
    public static <T> T ofMap(
            Map<String, Object> source,
            Class<T> targetClass,
            String... ignoreProperties
    ) {
        T result = ClassUtils.newInstance(targetClass);

        for (Map.Entry<String, Object> entry : source.entrySet()) {
            if (ArrayUtils.contains(ignoreProperties, entry.getKey())) {
                continue;
            }
            Field field = ReflectionUtils.findField(result.getClass(), entry.getKey());
            if (Objects.isNull(field)) {
                continue;
            }

            field.setAccessible(true);
            ReflectionUtils.setField(field, result, cast(entry.getValue(), field.getType()));
        }

        return result;
    }

    /**
     * 集合转换器的实现
     *
     * @author maurice.chen
     */
    @SuppressWarnings("rawtypes")
    private static class CollectionConverter implements Converter {

        @Override
        public <T> T convert(
                Class<T> type,
                Object value
        ) {
            Class<?> typeInstance;

            if (type.isInterface() && Set.class.isAssignableFrom(type)) {
                typeInstance = LinkedHashSet.class;
            }
            else if (type.isInterface() && (List.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type))) {
                typeInstance = LinkedList.class;
            }
            else if (!type.isInterface()) {
                typeInstance = type;
            }
            else {
                typeInstance = value.getClass();
            }

            Object obj = ClassUtils.newInstance(typeInstance);
            Collection<?> collection = null;

            if (Collection.class.isAssignableFrom(obj.getClass())) {
                collection = (Collection<?>) obj;
            }

            if (collection == null) {
                return type.cast(value);
            }

            if (Collection.class.isAssignableFrom(value.getClass())) {
                Collection values = (Collection) value;
                collection.addAll(values);
            }

            return type.cast(obj);
        }
    }

    /**
     * Map 类型转换器实现
     *
     * @author maurice.chen
     */
    private static class MapConverter implements Converter {

        @Override
        public <T> T convert(
                Class<T> aClass,
                Object o
        ) {
            if (String.class.isAssignableFrom(o.getClass())) {
                return SystemException.convertSupplier(() -> getObjectMapper().readValue(o.toString(), aClass));
            }
            return convertValue(o, aClass);
        }
    }

    static {
        registerDateConverter(DateUtils.DEFAULT_DATE_FORMATTER_PATTERN, DateUtils.DEFAULT_DATE_TIME_FORMATTER_PATTERN);
        registerCollectionConverter();
        ConvertUtils.register(new MapConverter(), Map.class);
    }

    /**
     * 注册集合类型的转换器
     */
    private static void registerCollectionConverter() {
        ConvertUtils.register(new CollectionConverter(), Collection.class);
        ConvertUtils.register(new CollectionConverter(), List.class);
        ConvertUtils.register(new CollectionConverter(), ArrayList.class);
        ConvertUtils.register(new CollectionConverter(), LinkedList.class);
        ConvertUtils.register(new CollectionConverter(), Set.class);
        ConvertUtils.register(new CollectionConverter(), HashSet.class);
        ConvertUtils.register(new CollectionConverter(), LinkedHashSet.class);
    }

    /**
     * 注册一个时间类型的转换器,当前默认的格式为：yyyy-MM-dd
     *
     * @param patterns 日期格式
     */
    private static void registerDateConverter(String... patterns) {
        DateConverter dc = new DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(patterns);
        ConvertUtils.register(dc, Date.class);
    }
}
