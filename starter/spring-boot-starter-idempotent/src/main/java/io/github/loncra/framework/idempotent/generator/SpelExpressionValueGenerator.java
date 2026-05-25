package io.github.loncra.framework.idempotent.generator;

import io.github.loncra.framework.commons.generator.SpringExpressionGenerator;
import io.github.loncra.framework.idempotent.annotation.Concurrent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 简单的 key 生成实现
 * <p>
 * 假如 {@link Concurrent}("abs[#i]-[#j]")
 * 注解在方法 method(int i,in j) 上,而 i = 1，j = 2 时，生成 key 为: #{@link SpelExpressionValueGenerator#getPrefix()} + abs1-2
 * </p>
 *
 * @author maurice.chen
 */
public class SpelExpressionValueGenerator implements ValueGenerator {

    /**
     * 默认的并发 key 前缀
     */
    private static final String DEFAULT_PREFIX = "spring:el:value:generator:";

    /**
     * 并发 key 前缀
     */
    private String prefix = DEFAULT_PREFIX;

    /**
     * spring el 表达式解析器
     */
    private final SpringExpressionGenerator springExpressionGenerator = new SpringExpressionGenerator();

    /**
     * 参数名称发现者，用于获取 Concurrent 注解下的方法参数细信息
     */
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public Object generate(
            String expression,
            Method method,
            Object... args
    ) {
        return springExpressionGenerator.generate(getPrefix(), expression, getVariables(method, args));
    }

    private Map<String, Object> getVariables(
            Method method,
            Object... args
    ) {
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        Map<String, Object> variables = new LinkedHashMap<>();

        if (ArrayUtils.isNotEmpty(args)) {
            for (int i = 0; i < (parameterNames != null ? parameterNames.length : 0); i++) {
                variables.put(parameterNames[i], args[i]);
            }
        }

        return variables;
    }

    @Override
    public boolean assertCondition(
            String condition,
            Method method,
            Object... args
    ) {
        Map<String, Object> variables = getVariables(method, args);
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        String expression = springExpressionGenerator.generate(StringUtils.EMPTY, condition, variables)
                .toString();
        Boolean value = springExpressionGenerator.getParser()
                .parseExpression(expression)
                .getValue(evaluationContext, Boolean.class);
        return Boolean.TRUE.equals(value);
    }


    /**
     * 获取默认的 key 前缀
     *
     * @return 默认的 key 前缀
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设置默认的 key 前缀
     *
     * @param prefix 默认的 key 前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
