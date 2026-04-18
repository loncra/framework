package io.github.loncra.framework.security.audit.elasticsearch.wildcard;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;
import org.apache.commons.lang3.BooleanUtils;

/**
 * 等于 null 的通配符实现
 *
 * @author maurice.chen
 */
public class EqnWildcardParser implements WildcardParser<BoolQuery.Builder> {

    private final static String DEFAULT_WILDCARD_VALUE = "eqn";

    private final static String DEFAULT_WILDCARD_NAME = "为 null";

    @Override
    public void structure(
            Property property,
            BoolQuery.Builder queryBuilder
    ) {
        if (BooleanUtils.toBoolean(property.getValue().toString())) {
            Query existsQuery = QueryBuilders.exists(e -> e.field(property.getFinalPropertyName()));
            queryBuilder.mustNot(existsQuery);
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
