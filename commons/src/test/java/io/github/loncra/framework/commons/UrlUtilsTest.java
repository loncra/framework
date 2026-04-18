package io.github.loncra.framework.commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * UrlUtils 测试类
 *
 * @author maurice.chen
 */
public class UrlUtilsTest {

    @Test
    public void testSetUrlPathVariableValue() {
        // 测试基本路径变量替换
        String url = "/api/users/{userId}/posts/{postId}";
        Map<String, String> variableValue = new HashMap<>();
        variableValue.put("userId", "123");
        variableValue.put("postId", "456");

        String result = UrlUtils.setUrlPathVariableValue(url, variableValue);
        Assertions.assertEquals("/api/users/123/posts/456", result);

        // 测试部分变量替换
        String partialUrl = "/api/users/{userId}/posts/{postId}";
        Map<String, String> partialVariableValue = new HashMap<>();
        partialVariableValue.put("userId", "123");

        String partialResult = UrlUtils.setUrlPathVariableValue(partialUrl, partialVariableValue);
        Assertions.assertEquals("/api/users/123/posts/{postId}", partialResult);

        // 测试没有路径变量的 URL
        String noVariableUrl = "/api/users/posts";
        String noVariableResult = UrlUtils.setUrlPathVariableValue(noVariableUrl, variableValue);
        Assertions.assertEquals("/api/users/posts", noVariableResult);

        // 测试空 map
        String emptyMapResult = UrlUtils.setUrlPathVariableValue(url, new HashMap<>());
        Assertions.assertEquals(url, emptyMapResult);

        // 测试带空格的变量名
        String spacedUrl = "/api/{userId }/posts";
        Map<String, String> spacedVariableValue = new HashMap<>();
        spacedVariableValue.put("userId ", "123");
        String spacedResult = UrlUtils.setUrlPathVariableValue(spacedUrl, spacedVariableValue);
        Assertions.assertEquals("/api/{userId }/posts", spacedResult);

        // 测试多个相同变量
        String duplicateUrl = "/api/{id}/users/{id}";
        Map<String, String> duplicateVariableValue = new HashMap<>();
        duplicateVariableValue.put("id", "123");
        String duplicateResult = UrlUtils.setUrlPathVariableValue(duplicateUrl, duplicateVariableValue);
        Assertions.assertEquals("/api/123/users/123", duplicateResult);
    }
}

