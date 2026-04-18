package io.github.loncra.framework.security.audit.elasticsearch.wildcard;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import io.github.loncra.framework.commons.jackson.serializer.DesensitizeSerializer;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;

/**
 * 模糊查询的左匹配通配符实现
 *
 * @author maurice.chen
 */
public class LikeLeftWildcardParser implements WildcardParser<BoolQuery.Builder> {

    private final static String DEFAULT_WILDCARD_VALUE = "llike";

    private final static String DEFAULT_WILDCARD_NAME = "从左开始模糊查询";

    @Override
    public void structure(
            Property property,
            BoolQuery.Builder queryBuilder
    ) {
        String value = DesensitizeSerializer.DEFAULT_DESENSITIZE_SYMBOL + property.getValue().toString();

        Query wildcardQuery = QueryBuilders.wildcard(w -> w
                .field(property.getFinalPropertyName())
                .value(value)
        );
        queryBuilder.must(wildcardQuery);
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
