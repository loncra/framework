package io.github.loncra.framework.commons.generator;

import io.github.loncra.framework.commons.annotation.Metadata;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Function;

/**
 * 将 {@link Metadata#value()} 中的 SpEL 模板（经 {@link SpringExpressionGenerator}）在给定变量上下文中求值；
 * 用于控制器审计 {@link Metadata} 扩展字段及 idempotent 等场景。
 *
 * @author maurice.chen
 */
public class SpringExpressionMetadataGenerator implements Function<Metadata, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringExpressionMetadataGenerator.class);

    private final SpringExpressionGenerator springExpressionGenerator = new SpringExpressionGenerator();

    private final Map<String, Object> variables;

    /**
     * @param variables SpEL 根变量（如 {@code metadata}、{@code principal}）
     */
    public SpringExpressionMetadataGenerator(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Override
    public Object apply(Metadata metadata) {
        try {
            return springExpressionGenerator.generate(StringUtils.EMPTY, metadata.value(), variables);
        } catch (Exception e) {
            LOGGER.warn("解析值错误,直接返回原始值,错误信息为:", e);
            return metadata.value();
        }
    }
}
