package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

/**
 * 不等于查询通配符实现
 *
 * @author maurice.chen
 */
public class NeWildcardParser<T> implements WildcardParser<QueryWrapper<T>> {

    private final static String DEFAULT_WILDCARD_VALUE = "ne";

    private final static String DEFAULT_WILDCARD_NAME = "不等于";

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {
        queryWrapper.ne(property.getFinalPropertyName(), property.getValue());
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
