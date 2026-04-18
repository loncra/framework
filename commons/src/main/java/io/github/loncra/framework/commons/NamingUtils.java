package io.github.loncra.framework.commons;

import org.apache.commons.lang3.StringUtils;

/**
 * 命名转换工具类
 *
 * @author maurice.chen
 */
public abstract class NamingUtils {

    /**
     * 下划线符号
     */
    public static final String UNDERSCORE = "_";

    /**
     * 将驼峰命名转换为蛇形命名（snake_case）
     *
     * @param name 驼峰命名的字符串
     *
     * @return 蛇形命名的字符串
     */
    public static String toSnakeCase(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        StringBuilder result = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(UNDERSCORE).append(Character.toLowerCase(c));
            }
            else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     * 将蛇形命名转换为驼峰命名
     *
     * @param snakeCase 蛇形命名
     *
     * @return 驼峰命名名称
     */
    public static String castSnakeCaseToCamelCase(String snakeCase) {
        // 首先将字段名按下划线分割成单词
        String[] words = snakeCase.split(UNDERSCORE);

        // 如果只有一个单词，无需转换，直接返回
        if (words.length == 1) {
            return words[0];
        }

        // 从第二个单词开始，将首字母大写
        StringBuilder result = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }

        return result.toString();
    }

    /**
     * 将驼峰命名转换为蛇形命名
     *
     * @param camelCase 驼峰命名
     *
     * @return 蛇形命名名称
     */
    public static String castCamelCaseToSnakeCase(String camelCase) {
        StringBuilder snakeCase = new StringBuilder();
        boolean isFirst = true;

        for (char c : camelCase.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (!isFirst) {
                    snakeCase.append(UNDERSCORE);
                }
                snakeCase.append(Character.toLowerCase(c));
            }
            else {
                snakeCase.append(c);
            }
            isFirst = false;
        }

        return snakeCase.toString();
    }
}

