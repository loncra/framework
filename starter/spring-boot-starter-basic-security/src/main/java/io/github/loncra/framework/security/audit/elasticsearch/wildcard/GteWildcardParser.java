package io.github.loncra.framework.security.audit.elasticsearch.wildcard;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

/**
 * 大于等于查询通配符实现
 *
 * @author maurice.chen
 */
public class GteWildcardParser implements WildcardParser<BoolQuery.Builder> {

    private final static String DEFAULT_WILDCARD_VALUE = "gte";

    private final static String DEFAULT_WILDCARD_NAME = "大于等于";

    @Override
    public void structure(
            Property property,
            BoolQuery.Builder queryBuilder
    ) {
        RangeQuery.Builder rangeBuilder = new RangeQuery.Builder();
        rangeBuilder.term(t -> t.field(property.getFinalPropertyName()).gte(property.getValue().toString()));

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