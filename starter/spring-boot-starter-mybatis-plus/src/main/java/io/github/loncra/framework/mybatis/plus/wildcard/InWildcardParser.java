package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 等于查询通配符实现
 *
 * @author maurice.chen
 */
public class InWildcardParser<T> implements WildcardParser<QueryWrapper<T>> {

    private final static String DEFAULT_WILDCARD_VALUE = "in";

    private final static String DEFAULT_WILDCARD_NAME = "在列表里 (in)";

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {
        if (Iterable.class.isAssignableFrom(property.getValue().getClass())) {

            Iterable<?> iterable = CastUtils.cast(property.getValue());
            List<Object> values = new ArrayList<>();

            iterable.forEach(values::add);
            queryWrapper.in(property.getFinalPropertyName(), values.toArray());
        }
        else {
            queryWrapper.in(property.getFinalPropertyName(), property.getValue());
        }
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
