package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 范围查询通配符实现
 *
 * @author maurice.chen
 */
public class BetweenWildcardParser<T> implements WildcardParser<QueryWrapper<T>> {

    private final static String DEFAULT_WILDCARD_VALUE = "between";

    private final static String DEFAULT_WILDCARD_NAME = "范围查询";

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {

        final List<Object> values = new ArrayList<>();
        if (property.getValue().getClass().isArray()) {
            values.addAll(List.of((Object[]) property.getValue()));
        }
        else if (property.getValue() instanceof Iterable<?> iterable) {
            iterable.forEach(t -> values.add(t.toString()));
        }
        else {
            values.add(property.getValue());
        }

        List<Object> finalValues = values.stream().filter(Objects::nonNull).toList();
        SystemException.isTrue(!finalValues.isEmpty(), "Between 查询至少需要一个参数值");

        Object startValue = values.iterator().next();

        // 如果只有一个参数，使用 ge（大于等于）查询
        if (values.size() == 1) {
            queryWrapper.ge(property.getFinalPropertyName(), startValue);
        }
        else {
            // 如果有两个或更多参数，使用 between 进行范围查询
            // 使用第一个参数作为起始值，最后一个参数作为结束值
            Object endValue = values.get(values.size() - 1);
            queryWrapper.between(property.getFinalPropertyName(), startValue, endValue);
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
