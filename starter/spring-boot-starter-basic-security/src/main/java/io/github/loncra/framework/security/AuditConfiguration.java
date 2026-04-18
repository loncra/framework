package io.github.loncra.framework.security;


import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.security.audit.AuditType;
import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchAuditConfiguration;
import io.github.loncra.framework.security.audit.memory.CustomInMemoryAuditConfiguration;
import io.github.loncra.framework.security.audit.mongo.MongoAuditConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

import java.util.*;

/**
 * 审计自动配置
 *
 * @author maurice.chen
 */
@Configuration
@EnableConfigurationProperties(AuditProperties.class)
@Import(AuditConfiguration.AuditImportSelector.class)
@ConditionalOnProperty(prefix = "loncra.framework.security.audit", value = "enabled", matchIfMissing = true)
public class AuditConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuditConfiguration.class);

    private static final Map<AuditType, Class<?>> MAPPINGS;

    static {
        Map<AuditType, Class<?>> mappings = new LinkedHashMap<>();

        mappings.put(AuditType.Memory, CustomInMemoryAuditConfiguration.class);
        mappings.put(AuditType.Elasticsearch, ElasticsearchAuditConfiguration.class);
        mappings.put(AuditType.Mongo, MongoAuditConfiguration.class);

        MAPPINGS = Collections.unmodifiableMap(mappings);
    }

    /**
     * 审计导入选择器，用于根据配置的审计类型选择导入相应的配置类
     *
     * @author maurice.chen
     */
    public static class AuditImportSelector implements ImportSelector {

        /**
         * 选择要导入的配置类
         *
         * @param importingClassMetadata 导入类的元数据
         *
         * @return 要导入的配置类名称数组
         */
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            List<String> imports = new LinkedList<>();

            for (AuditType auditType : AuditType.values()) {
                if (MAPPINGS.containsKey(auditType)) {
                    imports.add(MAPPINGS.get(auditType).getName());
                }
            }
            return imports.toArray(new String[0]);
        }
    }

    /**
     * 审计导入选择器条件，用于根据配置的审计类型判断是否匹配
     *
     * @author maurice.chen
     */
    public static class AuditImportSelectorCondition extends SpringBootCondition {

        /**
         * 获取匹配结果
         *
         * @param context  条件上下文
         * @param metadata 注解类型元数据
         *
         * @return 条件匹配结果
         */
        @Override
        public ConditionOutcome getMatchOutcome(
                ConditionContext context,
                AnnotatedTypeMetadata metadata
        ) {

            String sourceClass = "";

            if (ClassMetadata.class.isAssignableFrom(metadata.getClass())) {
                ClassMetadata classMetadata = CastUtils.cast(metadata);
                sourceClass = classMetadata.getClassName();
            }

            ConditionMessage.Builder message = ConditionMessage.forCondition("Audit", sourceClass);
            Environment environment = context.getEnvironment();

            try {

                BindResult<AuditType> specified = Binder
                        .get(environment)
                        .bind("loncra.framework.security.audit.type", AuditType.class);

                if (AnnotationMetadata.class.isAssignableFrom(metadata.getClass())) {

                    AnnotationMetadata annotationMetadata = CastUtils.cast(metadata);

                    AuditType required = AuditConfiguration.getType(annotationMetadata.getClassName());

                    if (!specified.isBound()) {
                        return AuditType.Memory.equals(required) ?
                                ConditionOutcome.match(message.because(AuditType.Memory + " audit type")) :
                                ConditionOutcome.noMatch(message.because("unknown audit type"));
                    }
                    else if (required.equals(specified.get())) {
                        return ConditionOutcome.match(message.because(specified.get() + " audit type"));
                    }

                }

            }
            catch (BindException ex) {
                LOGGER.warn("在执行审计内容自动根据类型注入时出现错误", ex);
            }

            return ConditionOutcome.noMatch(message.because("unknown audit type"));

        }

    }

    /**
     * 根据配置类名称获取审计类型
     *
     * @param configurationClassName 配置类名称
     *
     * @return 审计类型
     *
     * @throws IllegalStateException 如果找不到对应的审计类型
     */
    public static AuditType getType(String configurationClassName) {
        for (Map.Entry<AuditType, Class<?>> entry : MAPPINGS.entrySet()) {
            if (entry.getValue().getName().equals(configurationClassName)) {
                return entry.getKey();
            }
        }
        String msg = "unknown configuration class" + configurationClassName;
        throw new IllegalStateException(msg);
    }


}
