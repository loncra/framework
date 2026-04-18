package io.github.loncra.framework.security.audit.elasticsearch.wildcard;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
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
public class BetweenWildcardParser implements WildcardParser<BoolQuery.Builder> {

    private final static String DEFAULT_WILDCARD_VALUE = "between";

    private final static String DEFAULT_WILDCARD_NAME = "范围查询";

    @Override
    public void structure(
            Property property,
            BoolQuery.Builder queryBuilder
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

        RangeQuery.Builder rangeBuilder = new RangeQuery.Builder();

        Object startValue = finalValues.iterator().next();

        // 如果只有一个参数，使用 gte（大于等于）查询
        if (finalValues.size() == 1) {
            rangeBuilder.term(t -> t.field(property.getFinalPropertyName()).gte(startValue.toString()));
        }
        else {
            // 如果有两个或更多参数，使用 gte 和 lte 组合进行范围查询
            // 使用第一个参数作为起始值（gte），最后一个参数作为结束值（lte）
            Object endValue = finalValues.get(finalValues.size() - 1);
            rangeBuilder.term(t -> t.field(property.getFinalPropertyName())
                    .gte(startValue.toString())
                    .lte(endValue.toString()));
        }

        Query rangeQuery = Query.of(q -> q.range(rangeBuilder.build()));
        queryBuilder.must(rangeQuery);
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