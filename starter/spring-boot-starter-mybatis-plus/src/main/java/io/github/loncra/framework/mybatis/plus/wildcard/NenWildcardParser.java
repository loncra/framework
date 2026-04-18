package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;
import org.apache.commons.lang3.BooleanUtils;

/**
 * 不等于 null 的通配符实现
 *
 * @author maurice.chen
 */
public class NenWildcardParser<T> implements WildcardParser<QueryWrapper<T>> {

    private final static String DEFAULT_WILDCARD_VALUE = "nen";

    private final static String DEFAULT_WILDCARD_NAME = "不为 null";

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {
        if (BooleanUtils.toBoolean(property.getValue().toString())) {
            queryWrapper.isNotNull(property.getFinalPropertyName());
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
