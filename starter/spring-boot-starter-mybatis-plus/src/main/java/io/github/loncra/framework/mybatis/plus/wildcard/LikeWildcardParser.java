package io.github.loncra.framework.mybatis.plus.wildcard;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.query.Property;
import io.github.loncra.framework.commons.query.generator.WildcardParser;
import org.apache.commons.lang3.Strings;

import java.util.LinkedList;
import java.util.List;

/**
 * 模糊查询通配符实现
 *
 * @author maurice.chen
 */
public class LikeWildcardParser<T> implements WildcardParser<QueryWrapper<T>> {

    private final static String DEFAULT_WILDCARD_VALUE = "like";

    private final static String DEFAULT_WILDCARD_NAME = "模糊查询";

    /**
     * 匹配符号
     */
    public static final String MATCH_SYMBOL = "%";

    /**
     * 为属性值添加匹配符号
     * <p>如果属性值不包含匹配符号，则在值的前后添加 % 符号</p>
     *
     * @param property 属性对象
     */
    public static void addMatchSymbol(Property property) {
        if (property.getValue() instanceof List<?>) {
            List<?> list = CastUtils.cast(property.getValue());
            List<String> newValue = new LinkedList<>();
            for (Object value : list) {
                String stringValue = value.toString();
                if (Strings.CS.startsWith(stringValue, MATCH_SYMBOL) || Strings.CS.endsWith(stringValue, MATCH_SYMBOL)) {
                    newValue.add(stringValue);
                }
                else {
                    newValue.add(MATCH_SYMBOL + stringValue + MATCH_SYMBOL);
                }
            }
            property.setValue(newValue);
        }
        else {
            String stringValue = property.getValue().toString();
            if (!Strings.CS.startsWith(stringValue, MATCH_SYMBOL) && !Strings.CS.endsWith(stringValue, MATCH_SYMBOL)) {
                property.setValue(MATCH_SYMBOL + stringValue + MATCH_SYMBOL);
            }
        }
    }

    @Override
    public void structure(
            Property property,
            QueryWrapper<T> queryWrapper
    ) {
        queryWrapper.like(property.getFinalPropertyName(), property.getValue());
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
