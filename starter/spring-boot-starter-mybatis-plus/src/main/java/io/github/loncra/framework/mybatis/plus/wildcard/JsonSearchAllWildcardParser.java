package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.query.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/**
 * json 查询的返回所有匹配项的路径通配符解析器实现
 *
 * @param <T>
 *
 * @author maurice.chen
 */
public class JsonSearchAllWildcardParser<T> extends AbstractJsonFunctionWildcardParser<T> {

    private final static String DEFAULT_WILDCARD_VALUE = "jsa";

    private final static String DEFAULT_WILDCARD_NAME = "Json 数据格式 all 查询";

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {
        LikeWildcardParser.addMatchSymbol(property);
        super.structure(property, queryWrapper);
    }

    /**
     * 获取表达式
     *
     * @param property 属性
     * @param index    值索引
     *
     * @return JSON_CONTAINS 表达式
     */
    @Override
    public String getExpression(
            Property property,
            Integer index
    ) {

        if (Strings.CS.contains(property.getPropertyName(), CastUtils.DOT)) {
            String path = StringUtils.substringAfter(property.getPropertyName(), CastUtils.DOT);
            String field = StringUtils.substringBefore(property.getPropertyName(), CastUtils.DOT);
            return "JSON_SEARCH(" + property.splicePropertyName(field) + "->'$" + getFinalPath(path) + "', 'all', {" + index + "}, '$') IS NOT NULL";
        }

        return "JSON_SEARCH(" + property.getFinalPropertyName() + ", 'all', {" + index + "}) IS NOT NULL";
    }

    @Override
    public boolean isSupport(String condition) {
        return DEFAULT_WILDCARD_VALUE.equals(condition);
    }

    @Override
    public String getName() {
        return DEFAULT_WILDCARD_NAME;
    }

    @Override
    public String getValue() {
        return DEFAULT_WILDCARD_VALUE;
    }
}
