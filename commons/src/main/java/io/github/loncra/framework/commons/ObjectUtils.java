package io.github.loncra.framework.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.github.loncra.framework.commons.annotation.IgnoreField;
import io.github.loncra.framework.commons.jackson.serializer.DesensitizeSerializer;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 对象工具类
 *
 * @author maurice.chen
 */
public abstract class ObjectUtils {

    /**
     * 将对象转换为 Map，并忽略指定的字段
     *
     * @param source     源对象
     * @param properties 要忽略的字段路径列表
     *
     * @return 转换后的 Map
     */
    public static Map<String, Object> ignoreObjectFieldToMap(
            Object source,
            List<String> properties
    ) {
        DocumentContext documentContext = createDocumentContext(source, Option.DEFAULT_PATH_LEAF_TO_NULL);
        properties.forEach(documentContext::delete);
        return documentContext.json();
    }

    /**
     * 获取维度值
     *
     * @param count 维度数量
     * @param args  取模 hash 参数
     *
     * @return 维度值
     */
    public static String getDimension(
            Integer count,
            Object... args
    ) {
        return String.format("%0" + String.valueOf(count).length() + "d", Math.abs(Objects.hash(args) % count + 1));
    }

    /**
     * 将对象转换为 Map，并对指定的字段进行脱敏处理
     *
     * @param source     源对象
     * @param properties 要进行脱敏的字段路径列表
     *
     * @return 转换后的 Map
     */
    public static Map<String, Object> desensitizeObjectFieldToMap(
            Object source,
            List<String> properties
    ) {
        DocumentContext documentContext = createDocumentContext(source, Option.DEFAULT_PATH_LEAF_TO_NULL);
        for (String property : properties) {
            Object value = documentContext.read(property);
            if (Objects.isNull(value)) {
                continue;
            }
            if (value instanceof JSONArray array) {
                Configuration pathConfig = Configuration.builder()
                        .options(Option.SUPPRESS_EXCEPTIONS, Option.AS_PATH_LIST)
                        .build();
                List<String> paths = JsonPath.using(pathConfig).parse(documentContext.jsonString()).read(property);
                if (CollectionUtils.isEmpty(paths)) {
                    continue;
                }
                for (String path : paths) {
                    List<String> values = new LinkedList<>();
                    for (Object item : array) {
                        if (Objects.isNull(item)) {
                            continue;
                        }
                        String desensitizeValue = Objects.toString(item, StringUtils.EMPTY);
                        values.add(DesensitizeSerializer.desensitize(desensitizeValue));
                    }
                    documentContext.set(path, values);
                }

            }
            else {
                documentContext.set(property, DesensitizeSerializer.desensitize(value.toString()));
            }
        }
        return documentContext.json();
    }

    /**
     * 获取类中标记了 @IgnoreField 注解的字段列表
     *
     * @param targetClass 目标类
     *
     * @return 需要忽略的字段列表
     */
    public static List<Field> getIgnoreField(Class<?> targetClass) {
        List<Field> fields = new LinkedList<>();
        for (Field o : targetClass.getDeclaredFields()) {
            IgnoreField ignoreField = o.getAnnotation(IgnoreField.class);
            if (Objects.isNull(ignoreField)) {
                continue;
            }
            fields.add(o);
        }
        return fields;
    }

    /**
     * 判断类型是否为基本类型或字符串类型
     *
     * @param value 类型
     *
     * @return true 如果是基本类型或字符串类型，否则 false
     */
    public static boolean isPrimitive(Class<?> value) {
        return (Boolean.class.isAssignableFrom(value) ||
                Byte.class.isAssignableFrom(value) ||
                Character.class.isAssignableFrom(value) ||
                Short.class.isAssignableFrom(value) ||
                Integer.class.isAssignableFrom(value) ||
                Long.class.isAssignableFrom(value) ||
                Float.class.isAssignableFrom(value) ||
                Double.class.isAssignableFrom(value)) ||
                String.class.isAssignableFrom(value);
    }

    private static DocumentContext createDocumentContext(
            Object source,
            Option option
    ) {
        ObjectMapper objectMapper = CastUtils.getObjectMapper();
        JsonNode rootNode = objectMapper.valueToTree(source);
        JsonNode filteredNode = rootNode.deepCopy();

        Configuration conf = Configuration.builder()
                .options(Option.SUPPRESS_EXCEPTIONS, option)
                .build();

        return JsonPath.using(conf).parse(filteredNode.toString());
    }
}

