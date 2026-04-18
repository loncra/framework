package io.github.loncra.framework.security.audit.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.query.QueryGenerator;
import io.github.loncra.framework.commons.query.condition.Condition;
import io.github.loncra.framework.commons.query.condition.ConditionParser;
import io.github.loncra.framework.commons.query.condition.ConditionType;
import io.github.loncra.framework.commons.query.condition.support.SimpleConditionParser;
import io.github.loncra.framework.commons.query.generator.WildcardParser;
import io.github.loncra.framework.security.audit.elasticsearch.wildcard.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Elasticsearch 查询生成器实现
 *
 * @author maurice.chen
 */
public class ElasticsearchQueryGenerator implements QueryGenerator<BoolQuery.Builder> {

    /**
     * 通配符解析器集合
     */
    private final List<WildcardParser<BoolQuery.Builder>> wildcardParsers;

    /**
     * 条件解析器集合
     */
    private final List<ConditionParser> conditionParsers;

    /**
     * 创建一个 Elasticsearch 查询生成器实现
     */
    public ElasticsearchQueryGenerator() {
        this.wildcardParsers = getDefaultWildcardParserList();
        this.conditionParsers = getDefaultConditionParserList();
    }

    /**
     * 创建一个 Elasticsearch 查询生成器实现
     *
     * @param wildcardParsers  通配符解析器集合
     * @param conditionParsers 条件解析器集合
     */
    public ElasticsearchQueryGenerator(
            List<WildcardParser<BoolQuery.Builder>> wildcardParsers,
            List<ConditionParser> conditionParsers
    ) {
        this.wildcardParsers = wildcardParsers;
        this.conditionParsers = conditionParsers;
    }

    @Override
    public BoolQuery.Builder generate(Map<String, List<Condition>> filterConditionMap) {
        BoolQuery.Builder queryBuilder = QueryBuilders.bool();

        for (Map.Entry<String, List<Condition>> entry : filterConditionMap.entrySet()) {

            for (Condition c : entry.getValue()) {
                List<WildcardParser<BoolQuery.Builder>> result = getWildcardParserList()
                        .stream()
                        .filter(w -> w.isSupport(c.getName()))
                        .collect(Collectors.toList());

                if (CollectionUtils.isEmpty(result)) {
                    throw new SystemException("找不到 [" + c.getName() + "] 的表达式查询实现");
                }

                for (WildcardParser<BoolQuery.Builder> wildcardParser : result) {
                    BoolQuery.Builder conditionBuilder = QueryBuilders.bool();
                    wildcardParser.structure(c.getProperty(), conditionBuilder);

                    if (ConditionType.Or.equals(c.getType())) {
                        queryBuilder.should(conditionBuilder.build());
                    }
                    else {
                        queryBuilder.must(conditionBuilder.build());
                    }
                }
            }

            //queryBuilder.must(mustBuilder.build());
        }

        return queryBuilder;
    }

    @Override
    public List<WildcardParser<BoolQuery.Builder>> getWildcardParserList() {
        return wildcardParsers;
    }

    @Override
    public List<ConditionParser> getConditionParserList() {
        return conditionParsers;
    }

    /**
     * 获取默认通配符解析器集合
     *
     * @return 通配符解析器集合
     */
    public List<WildcardParser<BoolQuery.Builder>> getDefaultWildcardParserList() {
        return Arrays.asList(
                new BetweenWildcardParser(),
                new EqWildcardParser(),
                new NeWildcardParser(),
                new LikeWildcardParser(),
                new LikeRightWildcardParser(),
                new LikeLeftWildcardParser(),
                new GteWildcardParser(),
                new GtWildcardParser(),
                new LteWildcardParser(),
                new LtWildcardParser(),
                new InWildcardParser(),
                new NotInWildcardParser(),
                new EqnWildcardParser(),
                new NenWildcardParser()
        );
    }

    /**
     * 获取条件解析器集合
     *
     * @return 条件解析器集合
     */
    public List<ConditionParser> getDefaultConditionParserList() {
        return Collections.singletonList(new SimpleConditionParser());
    }
}

