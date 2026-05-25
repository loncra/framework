package io.github.loncra.framework.commons.generator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;

/**
 * 基于 Spring SpEL 的模板替换生成器：表达式中用 {@link #openCharacter}{@link #closeCharacter}（默认 {@code [}{@code ]}）包裹的片段作为 SpEL，
 * 在 {@link #generate(String, String, java.util.Map)} 中逐段求值并替换为字符串结果。
 * <p>供 {@link SpringExpressionMetadataGenerator}、{@code spring-boot-starter-idempotent} 的 {@code SpelExpressionValueGenerator} 等复用。</p>
 *
 * @author maurice.chen
 */
public class SpringExpressionGenerator {

    /**
     * 变量截取的开始字符
     */
    private String openCharacter = "[";

    /**
     * 变量截取的结束字符
     */
    private String closeCharacter = "]";

    /**
     * spring el 表达式解析器
     */
    private SpelExpressionParser parser = new SpelExpressionParser();

    public SpringExpressionGenerator() {
    }

    public SpringExpressionGenerator(SpelExpressionParser parser) {
        this.parser = parser;
    }

    public SpringExpressionGenerator(
            String openCharacter,
            String closeCharacter
    ) {
        this.openCharacter = openCharacter;
        this.closeCharacter = closeCharacter;
    }

    public SpringExpressionGenerator(
            String openCharacter,
            String closeCharacter,
            SpelExpressionParser parser
    ) {
        this(openCharacter, closeCharacter);
        this.parser = parser;
    }

    public Object generate(String expression, Map<String, Object> variables) {
        return generate(StringUtils.EMPTY, expression, variables);
    }

    public Object generate(
            String prefix,
            String expression,
            Map<String, Object> variables
    ) {

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        evaluationContext.setVariables(variables);

        List<String> tokens = new LinkedList<>();

        String[] array = StringUtils.substringsBetween(expression, openCharacter, closeCharacter);

        if (ArrayUtils.isNotEmpty(array)) {
            tokens = Arrays.asList(array);
        }

        String result = expression;

        List<String> replaceToken = new LinkedList<>();

        for (String t : tokens) {

            if (replaceToken.contains(t)) {
                continue;
            }

            Object value = parser.parseExpression(t).getValue(evaluationContext, String.class);

            if (Objects.nonNull(value)) {
                result = Strings.CS.replace(result, getTokenValue(t), value.toString());
            }
            else {
                result = Strings.CS.replace(result, getTokenValue(t), "null");
            }

            replaceToken.add(t);
        }

        return Strings.CS.prependIfMissing(result, prefix);
    }

    /**
     * 获取 token 值
     *
     * @param token token 值
     *
     * @return 添加开始和结束字符的 token 内容,如
     */
    private String getTokenValue(String token) {
        return openCharacter + token + closeCharacter;
    }



    /**
     * 获取变量截取的开始字符
     *
     * @return 变量截取的开始字符
     */
    public String getOpenCharacter() {
        return openCharacter;
    }

    /**
     * 设置变量截取的开始字符
     *
     * @param openCharacter 变量截取的开始字符
     */
    public void setOpenCharacter(String openCharacter) {
        this.openCharacter = openCharacter;
    }

    /**
     * 获取变量截取的结束字符
     *
     * @return 变量截取的结束字符
     */
    public String getCloseCharacter() {
        return closeCharacter;
    }

    /**
     * 设置变量截取的结束字符
     *
     * @param closeCharacter 变量截取的结束字符
     */
    public void setCloseCharacter(String closeCharacter) {
        this.closeCharacter = closeCharacter;
    }

    /**
     * 获取 spring el 解析器
     *
     * @return 解析器
     */
    public SpelExpressionParser getParser() {
        return parser;
    }
}
