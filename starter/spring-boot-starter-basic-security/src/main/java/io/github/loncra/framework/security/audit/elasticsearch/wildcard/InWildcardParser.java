package io.github.loncra.framework.security.audit.elasticsearch.wildcard;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 在列表里查询通配符实现
 *
 * @author maurice.chen
 */
public class InWildcardParser implements WildcardParser<BoolQuery.Builder> {

    private final static String DEFAULT_WILDCARD_VALUE = "in";

    private final static String DEFAULT_WILDCARD_NAME = "在列表里 (in)";

    @Override
    public void structure(
            Property property,
            BoolQuery.Builder queryBuilder
    ) {
        List<String> values = new ArrayList<>();

        if (Iterable.class.isAssignableFrom(property.getValue().getClass())) {
            Iterable<?> iterable = CastUtils.cast(property.getValue());
            iterable.forEach(value -> values.add(value.toString()));
        }
        else {
            values.add(property.getValue().toString());
        }

        Query termsQuery = Query
                .of(q -> q.terms(t -> t.field(property.getFinalPropertyName()).terms(terms -> terms.value(values.stream().map(FieldValue::of).toList()))));
        queryBuilder.must(termsQuery);
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