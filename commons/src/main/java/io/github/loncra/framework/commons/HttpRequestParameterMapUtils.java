package io.github.loncra.framework.commons;

import io.github.loncra.framework.commons.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * HTTP 请求参数 Map 工具类，用于解析与构造 query string、MultiValueMap 等
 *
 * @author maurice.chen
 */
public abstract class HttpRequestParameterMapUtils {

    /**
     * 问号
     */
    public static final String QUESTION_MARK = "?";

    /**
     * 默认等于符号
     */
    public static final String EQ = "=";

    /**
     * 默认 and 符号
     */
    public static final String HTTP_AND = "&";

    /**
     * 将格式为 http query string 的字符串转换为 MultiValueMap
     *
     * @param body 数据体
     *
     * @return 转换后的 MultiValueMap 对象
     */
    public static MultiValueMap<String, String> castRequestBodyMap(String body) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();

        String[] parts = StringUtils.split(body, HTTP_AND);
        if (parts != null) {
            Arrays.stream(parts).forEach(b -> {
                String key = StringUtils.substringBefore(b, EQ);
                String value = StringUtils.substringAfter(b, EQ);
                if (StringUtils.isNoneEmpty(key, value)) {
                    result.add(key, value);
                }
            });
        }

        return result;
    }

    /**
     * 将 MultiValueMap 对象转换为 name=value&amp;name2=value2&amp;name3=value3 格式字符串
     *
     * @param newRequestBody MultiValueMap 对象
     *
     * @return 转换后的字符串
     */
    public static <K, V> String castRequestBodyMapToString(MultiValueMap<K, V> newRequestBody) {
        return castRequestBodyMapToString(newRequestBody, Object::toString);
    }

    /**
     * 将 MultiValueMap 对象转换为 name=value&amp;name2=value2&amp;name3=value3 格式字符串
     *
     * @param newRequestBody MultiValueMap 对象
     * @param function       处理字符串的功能
     *
     * @return 转换后的字符串
     */
    public static <K, V> String castRequestBodyMapToString(
            MultiValueMap<K, V> newRequestBody,
            Function<V, String> function
    ) {
        StringBuilder result = new StringBuilder();

        newRequestBody
                .forEach((key, value) -> value
                        .forEach(
                                v -> result
                                        .append(key)
                                        .append(EQ)
                                        .append(value.size() > 1 ? value.stream().map(function).collect(Collectors.toList()) : function.apply(value.getFirst()))
                                        .append(HTTP_AND)
                        )
                );

        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }

    /**
     * 将数组值的 map 数据转换成 普通的 map 对象，如果值为 map 参数的值为1个以上的数组值，将该 key 对应的值转换成 list 对象
     *
     * @param map 要转换的 map 对象
     *
     * @return 新的 map 对象
     */
    public static <K, V> Map<K, Object> castArrayValueMapToObjectValueMap(Map<K, V[]> map) {
        return castArrayValueMapToObjectValueMap(map, s -> s);
    }

    /**
     * 将 key 为 String， value 为 String 数组的 map 数据转换成 key 为 String，value 为 object 的 map 对象
     *
     * @param map      key 为 String， value 为 String 数组的 map
     * @param function 处理字符串的功能
     *
     * @return key 为 String，value 为 object 的 map 对象
     */
    public static <K, V> Map<K, Object> castArrayValueMapToObjectValueMap(
            Map<K, V[]> map,
            Function<V, Object> function
    ) {
        Map<K, Object> result = new LinkedHashMap<>();

        map.forEach((k, v) -> {
            if (v.length > 1) {
                result.put(k, Arrays.stream(v).map(function).collect(Collectors.toList()));
            }
            else {
                result.put(k, function.apply(v[0]));
            }
        });

        return result;
    }

    /**
     * 将数组值的 map 数据转换成 MultiValueMap 对象
     *
     * @param map map 对象
     *
     * @return MultiValueMap
     */
    public static <K, V> MultiValueMap<K, V> castMapToMultiValueMap(Map<K, V[]> map) {
        return castMapToMultiValueMap(map, false);
    }

    /**
     * 将数组值的 map 数据转换成 MultiValueMap 对象
     *
     * @param map       map 对象
     * @param urlEncode 如果值为 String 类型是否 url 编码，true 是，否则 false
     *
     * @return MultiValueMap
     */
    @SuppressWarnings("unchecked")
    public static <K, V> MultiValueMap<K, V> castMapToMultiValueMap(
            Map<K, V[]> map,
            boolean urlEncode
    ) {
        MultiValueMap<K, V> result = new LinkedMultiValueMap<>();

        for (Map.Entry<K, V[]> entry : map.entrySet()) {
            List<V> values = Arrays
                    .stream(entry.getValue())
                    .map(v -> urlEncode ? (V) SystemException.convertSupplier(() -> URLEncoder.encode(v.toString(), Charset.defaultCharset())) : v)
                    .collect(Collectors.toList());
            result.put(entry.getKey(), values);
        }

        return result;
    }

    /**
     * 通过路径获取 map 实体
     *
     * @param source map 数据源
     * @param path   路径，多个以点(".")分割
     *
     * @return map 实体
     */
    public static Map<String, Object> getPathMap(
            Map<String, Object> source,
            String path
    ) {
        Map<String, Object> result = new LinkedHashMap<>(source);

        String[] strings = StringUtils.split(path, CastUtils.DOT);

        for (String s : strings) {
            result = CastUtils.cast(result.get(s));
        }

        return result;
    }
}

