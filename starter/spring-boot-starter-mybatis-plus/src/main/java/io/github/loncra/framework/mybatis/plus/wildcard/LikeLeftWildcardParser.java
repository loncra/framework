package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

/**
 * 模糊查询的左匹配通配符实现
 *
 * @author maurice.chen
 */
public class LikeLeftWildcardParser<T> implements WildcardParser<QueryWrapper<T>> {

    private final static String DEFAULT_WILDCARD_VALUE = "llike";

    private final static String DEFAULT_WILDCARD_NAME = "从左开始模糊查询";

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {
        queryWrapper.likeRight(property.getFinalPropertyName(), property.getValue());
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
