package io.github.loncra.framework.security.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.security.StoragePositionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * spring el 形式的存储定位生成器实现
 *
 * @author maurice.chen
 */
public class SpringElStoragePositioningGenerator implements StoragePositioningGenerator {

    /**
     * 类名变量键名
     */
    public final static String CLASS_NAME_VARIABLE_KEY = "className";

    /**
     * 日志记录器
     */
    public final static Logger LOGGER = LoggerFactory.getLogger(SpringElStoragePositioningGenerator.class);

    /**
     * Spring EL 表达式解析器
     */
    private final static ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 存储位置配置属性
     */
    private StoragePositionProperties storagePositionProperties;

    /**
     * 构造函数
     *
     * @param storagePositionProperties 存储位置配置属性
     */
    public SpringElStoragePositioningGenerator(StoragePositionProperties storagePositionProperties) {
        this.storagePositionProperties = storagePositionProperties;
    }

    @Override
    public String generatePositioning(Object object) {
        StandardEvaluationContext context = new StandardEvaluationContext();

        Map<String, Object> variables = CastUtils.convertValue(object, CastUtils.MAP_TYPE_REFERENCE);
        variables.put(CLASS_NAME_VARIABLE_KEY, object.getClass().getName());

        context.setVariables(variables);

        String suffix = "";

        for (String spel : storagePositionProperties.getSpringExpression()) {
            try {
                suffix = EXPRESSION_PARSER.parseExpression(spel).getValue(context, String.class);
                break;
            }
            catch (Exception e) {
                LOGGER.debug("对 spring 表达式:{}，执行出现错误,变量为:{}, 错误信息为:{}", spel, variables, e.getMessage());
            }
        }

        Assert.hasText(suffix, "执行 spring el 表达式生成定位失败,表达式为:" + storagePositionProperties.getSpringExpression() + ", 变量为:" + variables);
        return storagePositionProperties.getPrefix() + storagePositionProperties.getSeparator() + suffix;

    }

    /**
     * 获取审计存储位置配置属性
     *
     * @return 审计存储位置配置属性
     */
    public StoragePositionProperties getAuditStoragePositionProperties() {
        return storagePositionProperties;
    }

    /**
     * 设置审计存储位置配置属性
     *
     * @param storagePositionProperties 审计存储位置配置属性
     */
    public void setAuditStoragePositionProperties(StoragePositionProperties storagePositionProperties) {
        this.storagePositionProperties = storagePositionProperties;
    }
}
