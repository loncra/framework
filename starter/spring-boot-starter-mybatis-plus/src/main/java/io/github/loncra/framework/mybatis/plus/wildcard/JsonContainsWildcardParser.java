package io.github.loncra.framework.mybatis.plus.wildcard;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.query.Property;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/**
 * json 格式的包含查询通配符实现
 *
 * @author maurice.chen
 */
public class JsonContainsWildcardParser<T> extends AbstractJsonFunctionWildcardParser<T> {

    private final static String DEFAULT_WILDCARD_VALUE = "jin";

    private final static String DEFAULT_WILDCARD_NAME = "Json 数据格式值包含";

    @Override
    public boolean isSupport(String condition) {
        return DEFAULT_WILDCARD_VALUE.equals(condition);
    }

    @Override
    protected String getExpression(
            Property property,
            Integer index
    ) {

        if (Strings.CS.contains(property.getPropertyName(), CastUtils.DOT)) {
            String path = StringUtils.substringAfter(property.getPropertyName(), CastUtils.DOT);
            String field = StringUtils.substringBefore(property.getPropertyName(), CastUtils.DOT);
            return "JSON_CONTAINS(" + property.splicePropertyName(field) + "->'$" + getFinalPath(path) + "', JSON_ARRAY({" + index + "}), '$')";
        }

        return "JSON_CONTAINS(" + property.getFinalPropertyName() + ", JSON_ARRAY({" + index + "}))";
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
