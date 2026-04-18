package io.github.loncra.framework.commons.query.condition.support;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.condition.Condition;
import io.github.loncra.framework.commons.query.condition.ConditionParser;
import io.github.loncra.framework.commons.query.condition.ConditionType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 简单的条件解析器实现, 默认以 filter_ 最前缀的参数创建条件集合，具体格式为：
 * <p>
 * filter_[字段名_通配符]_and_[字段名_通配符]_or_[字段名_通配符]
 *
 * @author maurice.chen
 */
public class SimpleConditionParser implements ConditionParser {

    /**
     * 默认条件名称前缀
     */
    public static final String DEFAULT_CONDITION_NAME_PREFIX = "filter";

    /**
     * 默认数组符号
     */
    public static final String DEFAULT_ARRAY_SYMBOL = "[]";

    /**
     * 默认条件名称前缀
     */
    public static final String DEFAULT_FIELD_OPEN_PREFIX = "_[";

    /**
     * 默认字段结束识别符
     */
    public static final String DEFAULT_FIELD_CLOSE_SUFFIX = "]";

    /**
     * 默认的别名分隔符
     */
    public static final String DEFAULT_ALIAS_SEPARATOR = "->";

    /**
     * 条件名称前缀
     */
    private String conditionNamePrefix = DEFAULT_CONDITION_NAME_PREFIX;

    /**
     * 字段开始识别符
     */
    private String fieldOpenPrefix = DEFAULT_FIELD_OPEN_PREFIX;

    /**
     * 字段结束识别符
     */
    private String fieldCloseSuffix = DEFAULT_FIELD_CLOSE_SUFFIX;

    /**
     * 默认的数组符号
     */
    private String arraySymbol = DEFAULT_ARRAY_SYMBOL;

    /**
     * 字段条件分隔符
     */
    private String fieldConditionSeparators = CastUtils.UNDERSCORE;

    /**
     * 创建一个简单的条件解析器实现
     */
    public SimpleConditionParser() {
    }

    /**
     * 判断是否支持该参数名称
     *
     * @param name 参数名称
     *
     * @return true 如果支持，否则 false
     */
    @Override
    public boolean isSupport(String name) {
        return Strings.CS.startsWith(name, conditionNamePrefix + fieldConditionSeparators);
    }

    /**
     * 获取条件列表
     *
     * @param filter 过滤参数名称
     * @param value  参数值列表
     *
     * @return 条件列表
     */
    @Override
    public List<Condition> getCondition(
            String filter,
            List<Object> value
    ) {
        String name = Strings.CS.removeEnd(filter, arraySymbol);
        String[] fieldConditionList = StringUtils.substringsBetween(name, fieldOpenPrefix, fieldCloseSuffix);

        List<Condition> result = new LinkedList<>();

        if (ArrayUtils.isEmpty(fieldConditionList)) {
            return result;
        }

        for (String fieldCondition : fieldConditionList) {

            String propertyName = StringUtils.substringBeforeLast(fieldCondition, fieldConditionSeparators);
            Object propertyValue = value;

            if (CollectionUtils.isEmpty(value)) {
                propertyValue = null;
            }
            else if (value.size() == 1) {
                propertyValue = value.iterator().next();
            }

            if (Objects.isNull(propertyValue) || StringUtils.isBlank(propertyValue.toString())) {
                continue;
            }

            Property p;
            if (propertyName.contains(DEFAULT_ALIAS_SEPARATOR)) {
                String[] aliasSeparator = StringUtils.splitByWholeSeparator(propertyName, DEFAULT_ALIAS_SEPARATOR);
                p = new Property(aliasSeparator[0], aliasSeparator[1], propertyValue);
            }
            else {
                p = new Property(propertyName, propertyValue);
            }

            String condition = StringUtils.substringAfterLast(fieldCondition, fieldConditionSeparators);

            Condition c = new Condition(condition, ConditionType.And, p);

            result.add(c);
        }

        if (result.size() > 1) {
            result.forEach(condition -> condition.setType(ConditionType.Or));
        }

        return result;
    }

    /**
     * 设置条件名称前缀
     *
     * @param conditionNamePrefix 条件名称前缀
     */
    public void setConditionNamePrefix(String conditionNamePrefix) {
        this.conditionNamePrefix = conditionNamePrefix;
    }

    /**
     * 获取条件名称前缀
     *
     * @return 条件名称前缀
     */
    public String getConditionNamePrefix() {
        return conditionNamePrefix;
    }

    /**
     * 获取字段开始识别符
     *
     * @return 字段开始识别符
     */
    public String getFieldOpenPrefix() {
        return fieldOpenPrefix;
    }

    /**
     * 设置字段开始识别符
     *
     * @param fieldOpenPrefix 字段开始识别符
     */
    public void setFieldOpenPrefix(String fieldOpenPrefix) {
        this.fieldOpenPrefix = fieldOpenPrefix;
    }

    /**
     * 获取字段结束识别符
     *
     * @return 字段结束识别符
     */
    public String getFieldCloseSuffix() {
        return fieldCloseSuffix;
    }

    /**
     * 设置字段结束识别符
     *
     * @param fieldCloseSuffix 字段结束识别符
     */
    public void setFieldCloseSuffix(String fieldCloseSuffix) {
        this.fieldCloseSuffix = fieldCloseSuffix;
    }

    /**
     * 获取字段条件分隔符
     *
     * @return 字段条件分隔符
     */
    public String getFieldConditionSeparators() {
        return fieldConditionSeparators;
    }

    /**
     * 设置字段条件分隔符
     *
     * @param fieldConditionSeparators 字段条件分隔符
     */
    public void setFieldConditionSeparators(String fieldConditionSeparators) {
        this.fieldConditionSeparators = fieldConditionSeparators;
    }

    /**
     * 获取数组符号
     *
     * @return 数组符号
     */
    public String getArraySymbol() {
        return arraySymbol;
    }

    /**
     * 设置数组符号
     *
     * @param arraySymbol 数组符号
     */
    public void setArraySymbol(String arraySymbol) {
        this.arraySymbol = arraySymbol;
    }

    /**
     * 获取过滤令牌
     *
     * @param token 令牌值
     *
     * @return 格式化的过滤令牌字符串
     */
    public static String getFilterToken(String token) {
        return DEFAULT_CONDITION_NAME_PREFIX + DEFAULT_FIELD_OPEN_PREFIX + token + DEFAULT_FIELD_CLOSE_SUFFIX;
    }
}
