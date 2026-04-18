package io.github.loncra.framework.commons.query;

import io.github.loncra.framework.commons.query.condition.Condition;
import io.github.loncra.framework.commons.query.condition.ConditionParser;
import io.github.loncra.framework.commons.query.generator.WildcardParser;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询生成器，用于 http 提交 filter_ 前缀参数时，根据条件内容生成已给符合查询内容的对象
 *
 * @param <T> 查询结果类型
 *
 * @author maurice.chen
 */
public interface QueryGenerator<T> {

    /**
     * 生成内容
     *
     * @param filterConditionMap 条件信息
     *
     * @return 查询结果类型
     */
    T generate(Map<String, List<Condition>> filterConditionMap);

    /**
     * 获取通配符解析器集合
     *
     * @return 通配符解析器集合
     */
    List<WildcardParser<T>> getWildcardParserList();

    /**
     * 获取条件解析器集合
     *
     * @return 条件解析器集合
     */
    List<ConditionParser> getConditionParserList();

    /**
     * 通过 request 创建指定的查询对象
     *
     * @param request http servlet request
     *
     * @return 指定的查询对象
     */
    default T createQueryWrapperFromHttpRequest(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String, List<Object>> newValue = new LinkedHashMap<>();

        parameterMap.forEach((key, value) -> newValue.put(key, Arrays.asList(value)));

        return createQueryWrapperFromMap(new LinkedMultiValueMap<>(newValue));
    }

    /**
     * 通过 Map 创建指定的查询对象
     *
     * @param columnMap 列表表达式映射
     *
     * @return 指定的查询对象
     */
    default T createQueryWrapperFromMap(Map<String, Object> columnMap) {
        MultiValueMap<String, Object> queryMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
            if (entry.getValue() instanceof Collection<?> valueList) {
                queryMap.put(entry.getKey(), Arrays.asList(valueList.toArray()));
            }
            else {
                queryMap.add(entry.getKey(), entry.getValue());
            }
        }
        return createQueryWrapperFromMap(queryMap);
    }

    /**
     * 通过 MultiValueMap 创建指定的查询对象
     *
     * @param columnMap 列表表达式映射
     *
     * @return 指定的查询对象
     */
    default T createQueryWrapperFromMap(MultiValueMap<String, Object> columnMap) {
        // 创建条件
        List<Map.Entry<String, List<Object>>> entryList = columnMap
                .entrySet()
                .stream()
                // 过滤掉空的值
                .filter(e -> Objects.nonNull(e.getValue()))
                .toList();
        Map<String, List<Condition>> filterConditionMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Object>> entry : entryList) {
            List<Condition> conditions = getConditionParserList()
                    .stream()
                    .filter(c -> c.isSupport(entry.getKey()))
                    .flatMap(c -> c.getCondition(entry.getKey(), columnMap.get(entry.getKey())).stream())
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(conditions)) {
                filterConditionMap.put(entry.getKey(), conditions);
            }
        }

        return generate(filterConditionMap);
    }
}
