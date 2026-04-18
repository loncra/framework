package io.github.loncra.framework.commons;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * URL 工具类
 *
 * @author maurice.chen
 */
public abstract class UrlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    /**
     * 路径变量开始符号
     */
    public static final String HTTP_PATH_VARIABLE_START = "{";

    /**
     * 路径变量结束符号
     */
    public static final String HTTP_PATH_VARIABLE_END = "}";

    /**
     * 设置 url 路径变量值
     *
     * @param url           url 路径
     * @param variableValue url 路径的变量对应值 map
     *
     * @return 新的 url 路径
     */
    public static String setUrlPathVariableValue(
            String url,
            Map<String, String> variableValue
    ) {
        String[] vars = StringUtils.substringsBetween(url, HTTP_PATH_VARIABLE_START, HTTP_PATH_VARIABLE_END);
        if (vars == null) {
            return url;
        }

        List<String> varList = Arrays.asList(vars);
        List<String> existList = varList
                .stream()
                .map(StringUtils::trimToEmpty)
                .filter(variableValue::containsKey)
                .toList();

        String temp = url;
        for (String s : existList) {
            String searchString = HTTP_PATH_VARIABLE_START + s + HTTP_PATH_VARIABLE_END;
            temp = Strings.CS.replace(temp, searchString, variableValue.get(s));
        }

        return temp;
    }
}

