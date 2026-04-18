package io.github.loncra.framework.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpRequestParameterMapUtils 测试类
 *
 * @author maurice.chen
 */
public class HttpRequestParameterMapUtilsTest {

    @Test
    public void testCastRequestBodyMap() {
        // 测试正常情况
        String body = "name=value&key=test&id=123";
        MultiValueMap<String, String> result = HttpRequestParameterMapUtils.castRequestBodyMap(body);

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("value", result.getFirst("name"));
        Assertions.assertEquals("test", result.getFirst("key"));
        Assertions.assertEquals("123", result.getFirst("id"));

        // 测试空字符串
        MultiValueMap<String, String> emptyResult = HttpRequestParameterMapUtils.castRequestBodyMap("");
        Assertions.assertTrue(emptyResult.isEmpty());

        // 测试 null
        MultiValueMap<String, String> nullResult = HttpRequestParameterMapUtils.castRequestBodyMap(null);
        Assertions.assertTrue(nullResult.isEmpty());

        // 测试单个参数
        String singleParam = "name=value";
        MultiValueMap<String, String> singleResult = HttpRequestParameterMapUtils.castRequestBodyMap(singleParam);
        Assertions.assertEquals(1, singleResult.size());
        Assertions.assertEquals("value", singleResult.getFirst("name"));

        // 测试重复的 key
        String duplicateKey = "name=value1&name=value2";
        MultiValueMap<String, String> duplicateResult = HttpRequestParameterMapUtils.castRequestBodyMap(duplicateKey);
        Assertions.assertEquals(1, duplicateResult.size());
        Assertions.assertEquals(2, duplicateResult.get("name").size());
        Assertions.assertEquals("value1", duplicateResult.get("name").get(0));
        Assertions.assertEquals("value2", duplicateResult.get("name").get(1));

        // 测试没有值的参数
        String noValue = "name=&key=test";
        MultiValueMap<String, String> noValueResult = HttpRequestParameterMapUtils.castRequestBodyMap(noValue);
        Assertions.assertEquals(1, noValueResult.size());
        Assertions.assertEquals("test", noValueResult.getFirst("key"));
    }

    @Test
    public void testCastRequestBodyMapToString() {
        // 测试基本转换
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "value");
        map.add("key", "test");
        map.add("id", "123");

        String result = HttpRequestParameterMapUtils.castRequestBodyMapToString(map);
        Assertions.assertTrue(result.contains("name=value"));
        Assertions.assertTrue(result.contains("key=test"));
        Assertions.assertTrue(result.contains("id=123"));

        // 测试空 map
        MultiValueMap<String, String> emptyMap = new LinkedMultiValueMap<>();
        String emptyResult = HttpRequestParameterMapUtils.castRequestBodyMapToString(emptyMap);
        Assertions.assertEquals("", emptyResult);

        // 测试重复的 key
        MultiValueMap<String, String> duplicateMap = new LinkedMultiValueMap<>();
        duplicateMap.add("name", "value1");
        duplicateMap.add("name", "value2");
        String duplicateResult = HttpRequestParameterMapUtils.castRequestBodyMapToString(duplicateMap);
        Assertions.assertTrue(duplicateResult.contains("name=[value1, value2]"));
    }

    @Test
    public void testCastRequestBodyMapToStringWithFunction() {
        MultiValueMap<String, Integer> map = new LinkedMultiValueMap<>();
        map.add("count", 10);
        map.add("size", 20);

        String result = HttpRequestParameterMapUtils.castRequestBodyMapToString(map, Object::toString);
        Assertions.assertTrue(result.contains("count=10"));
        Assertions.assertTrue(result.contains("size=20"));

        // 测试自定义函数
        MultiValueMap<String, Integer> customMap = new LinkedMultiValueMap<>();
        customMap.add("value", 100);
        String customResult = HttpRequestParameterMapUtils.castRequestBodyMapToString(customMap, v -> "[" + v + "]");
        Assertions.assertTrue(customResult.contains("value=[100]"));
    }

    @Test
    public void testCastArrayValueMapToObjectValueMap() {
        // 测试单个值的数组
        Map<String, String[]> map = new LinkedHashMap<>();
        map.put("name", new String[] {"value"});
        map.put("key", new String[] {"test"});

        Map<String, Object> result = HttpRequestParameterMapUtils.castArrayValueMapToObjectValueMap(map);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("value", result.get("name"));
        Assertions.assertEquals("test", result.get("key"));

        // 测试多个值的数组
        Map<String, String[]> multiMap = new LinkedHashMap<>();
        multiMap.put("name", new String[] {"value1", "value2"});

        Map<String, Object> multiResult = HttpRequestParameterMapUtils.castArrayValueMapToObjectValueMap(multiMap);
        Assertions.assertEquals(1, multiResult.size());
        Assertions.assertInstanceOf(List.class, multiResult.get("name"));
        @SuppressWarnings("unchecked")
        List<String> nameList = (List<String>) multiResult.get("name");
        Assertions.assertEquals(2, nameList.size());
        Assertions.assertEquals("value1", nameList.get(0));
        Assertions.assertEquals("value2", nameList.get(1));
    }

    @Test
    public void testCastArrayValueMapToObjectValueMapWithFunction() {
        Map<String, Integer[]> map = new LinkedHashMap<>();
        map.put("count", new Integer[] {10, 20});

        Map<String, Object> result = HttpRequestParameterMapUtils.castArrayValueMapToObjectValueMap(map, i -> i * 2);
        Assertions.assertEquals(1, result.size());
        Assertions.assertInstanceOf(List.class, result.get("count"));
        @SuppressWarnings("unchecked")
        List<Integer> countList = (List<Integer>) result.get("count");
        Assertions.assertEquals(2, countList.size());
        Assertions.assertEquals(20, countList.get(0));
        Assertions.assertEquals(40, countList.get(1));
    }

    @Test
    public void testCastMapToMultiValueMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        map.put("name", new String[] {"value1", "value2"});
        map.put("key", new String[] {"test"});

        MultiValueMap<String, String> result = HttpRequestParameterMapUtils.castMapToMultiValueMap(map);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(2, result.get("name").size());
        Assertions.assertEquals(1, result.get("key").size());
        Assertions.assertEquals("value1", result.get("name").get(0));
        Assertions.assertEquals("value2", result.get("name").get(1));
        Assertions.assertEquals("test", result.get("key").get(0));
    }

    @Test
    public void testCastMapToMultiValueMapWithUrlEncode() {
        Map<String, String[]> map = new LinkedHashMap<>();
        map.put("name", new String[] {"value with space"});
        map.put("key", new String[] {"test&value"});

        MultiValueMap<String, String> result = HttpRequestParameterMapUtils.castMapToMultiValueMap(map, true);
        Assertions.assertEquals(2, result.size());
        // URL 编码后的值应该包含编码字符
        Assertions.assertTrue(result.get("name").get(0).contains("value+with+space") ||
                                      result.get("name").get(0).contains("value%20with%20space"));
    }

    @Test
    public void testGetPathMap() {
        Map<String, Object> source = new LinkedHashMap<>();
        Map<String, Object> nested = new LinkedHashMap<>();
        nested.put("nestedKey", "nestedValue");
        source.put("parent", nested);
        source.put("other", "value");

        Map<String, Object> result = HttpRequestParameterMapUtils.getPathMap(source, "parent");
        Assertions.assertEquals("nestedValue", result.get("nestedKey"));

        // 测试深层路径
        Map<String, Object> deepSource = new LinkedHashMap<>();
        Map<String, Object> level1 = new LinkedHashMap<>();
        Map<String, Object> level2 = new LinkedHashMap<>();
        level2.put("finalKey", "finalValue");
        level1.put("level2", level2);
        deepSource.put("level1", level1);

        Map<String, Object> deepResult = HttpRequestParameterMapUtils.getPathMap(deepSource, "level1.level2");
        Assertions.assertEquals("finalValue", deepResult.get("finalKey"));
    }

}

