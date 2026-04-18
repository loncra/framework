package io.github.loncra.framework.mybatis.plus.interceptor.tenant;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.TenantEntity;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;
import io.github.loncra.framework.mybatis.plus.tenant.TenantLinePolicy;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;

import java.util.*;

/**
 * 租户实体句柄实现：仅对「声明了 {@link TableName} 且实现 {@link TenantEntity}」的实体所映射的表拼接租户条件。
 * <p>通过 {@link #TenantEntityHandler(ApplicationContext, TenantLinePolicy)} 从已注册的 Spring Bean 中收集：仅处理带 MyBatis
 * {@link Mapper} 且继承 MyBatis-Plus {@link com.baomidou.mybatisplus.core.mapper.Mapper} 的接口，解析其泛型实体类型后
 * 判定；也可用 {@link #TenantEntityHandler(Set, TenantLinePolicy)} 直接传入物理表名集合。</p>
 * <p>租户行是否生效由 {@link TenantLinePolicy} 决定，接入方可注册自定义 Bean 扩展。</p>
 * <p>若全局配置了表前缀，请在 {@link TableName#value()} 中写完整表名（含前缀），与 MyBatis-Plus 实际表名一致。</p>
 *
 * @author maurice.chen
 */
public class TenantEntityHandler implements TenantLineHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantEntityHandler.class);

    /**
     * 规范化后的租户表名（小写、去引用、去 schema 前缀），与 {@link #ignoreTable(String)} 入参对齐比较。
     */
    private final Set<String> tenantTableNames;

    /**
     * 是否为本条 SQL 追加租户条件（接入方可替换为自定义 {@link TenantLinePolicy} Bean）。
     */
    private final TenantLinePolicy tenantLinePolicy;

    private MybatisPlusProperties mybatisPlusProperties;

    /**
     * 使用已解析好的租户表集合（通常为物理表名，大小写不敏感）。
     *
     * @param tenantTableNames 需要租户隔离的表名
     */
    public TenantEntityHandler(Set<String> tenantTableNames) {
        this(tenantTableNames, TenantLinePolicy.ALWAYS);
    }

    /**
     * 使用已解析好的租户表集合与租户行策略。
     *
     * @param tenantTableNames 需要租户隔离的表名
     * @param tenantLinePolicy 租户行策略，{@code null} 时等价于 {@link TenantLinePolicy#ALWAYS}
     */
    public TenantEntityHandler(Set<String> tenantTableNames, TenantLinePolicy tenantLinePolicy) {
        if (tenantTableNames == null || tenantTableNames.isEmpty()) {
            this.tenantTableNames = Set.of();
        }
        else {
            Set<String> normalized = new HashSet<>();
            for (String name : tenantTableNames) {
                String n = normalizeTableName(name);
                if (StringUtils.isNotEmpty(n)) {
                    normalized.add(n);
                }
            }
            this.tenantTableNames = Collections.unmodifiableSet(normalized);
        }
        this.tenantLinePolicy = tenantLinePolicy != null ? tenantLinePolicy : TenantLinePolicy.ALWAYS;
    }

    /**
     * 从 Spring 容器中已注册的 Mapper Bean 解析租户表：Mapper
     * {@link com.baomidou.mybatisplus.core.mapper.Mapper}，其泛型实体须实现 {@link TenantEntity} 且类层次上存在
     * {@link TableName}。
     *
     * @param applicationContext Spring 应用上下文（须为 {@link ConfigurableApplicationContext} 以便访问 Bean 定义）
     */
    public TenantEntityHandler(ApplicationContext applicationContext) {
        this(applicationContext, TenantLinePolicy.ALWAYS);
    }

    /**
     * 从 Spring 容器解析租户表，并使用指定租户行策略。
     *
     * @param applicationContext Spring 应用上下文（须为 {@link ConfigurableApplicationContext} 以便访问 Bean 定义）
     * @param tenantLinePolicy 租户行策略，{@code null} 时等价于 {@link TenantLinePolicy#ALWAYS}
     */
    public TenantEntityHandler(ApplicationContext applicationContext, TenantLinePolicy tenantLinePolicy) {
        this(collectTenantTableNamesFromMapperBeans(applicationContext), tenantLinePolicy);
        mybatisPlusProperties = applicationContext.getBean(MybatisPlusProperties.class);
    }

    @Override
    public Expression getTenantId() {
        SimpleTenantContext tenantContext = TenantContextHolder.get();
        if (!tenantLinePolicy.tenantIdSupport(tenantContext)) {
            return null;
        }
        String id = Objects.toString(TenantContextHolder.get().getId());
        if (StringUtils.isEmpty(id)) {
            throw new SystemException("当前请求未设置租户 ID");
        }
        return new StringValue(id);
    }

    @Override
    public boolean ignoreTable(String tableName) {
        SimpleTenantContext tenantContext = TenantContextHolder.get();
        if (!tenantLinePolicy.tenantIdSupport(tenantContext)) {
            return true;
        }
        if (tenantTableNames.isEmpty()) {
            return true;
        }
        return !tenantTableNames.contains(normalizeTableName(tableName));
    }

    private static Set<String> collectTenantTableNamesFromMapperBeans(ApplicationContext applicationContext) {
        if (!(applicationContext instanceof ConfigurableApplicationContext configurable)) {
            LOGGER.warn("ApplicationContext 非 ConfigurableApplicationContext，无法从 Mapper Bean 解析租户表，将不启用租户表隔离");
            return Set.of();
        }
        ConfigurableListableBeanFactory beanFactory = configurable.getBeanFactory();
        Set<String> names = new HashSet<>();
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            Class<?> mapperType;
            try {
                mapperType = beanFactory.getType(beanName);
            }
            catch (Exception e) {
                LOGGER.trace("跳过 Bean [{}] 类型解析: {}", beanName, e.getMessage());
                continue;
            }
            if (mapperType == null || !mapperType.isInterface()) {
                continue;
            }
            if (!Mapper.class.isAssignableFrom(mapperType)) {
                continue;
            }
            Class<?> entityClass = resolveMapperEntityType(mapperType);
            if (entityClass == null || !TenantEntity.class.isAssignableFrom(entityClass)) {
                continue;
            }
            TableName tableNameAnn = findTableNameAnnotation(entityClass);
            if (tableNameAnn == null) {
                continue;
            }
            String logicalName = resolveLogicalTableName(entityClass, tableNameAnn);
            if (StringUtils.isNotEmpty(logicalName)) {
                names.add(normalizeTableName(logicalName));
            }
        }
        if (names.isEmpty() && LOGGER.isDebugEnabled()) {
            LOGGER.debug("未从 Mapper Bean 解析到任何租户表（需 @Mapper + MP Mapper + TenantEntity + @TableName）");
        }
        return names;
    }

    /**
     * 从 Mapper 接口上 MyBatis-Plus {@link com.baomidou.mybatisplus.core.mapper.Mapper} 的泛型参数解析实体类型。
     */
    private static Class<?> resolveMapperEntityType(Class<?> mapperInterface) {
        return ResolvableType.forClass(mapperInterface)
                .as(com.baomidou.mybatisplus.core.mapper.Mapper.class)
                .getGeneric(0)
                .resolve();
    }

    @Override
    public String getTenantIdColumn() {
        String columnFormat = mybatisPlusProperties.getGlobalConfig()
                .getDbConfig()
                .getColumnFormat();
        if (StringUtils.isNotBlank(columnFormat)) {
            return String.format(columnFormat, TenantLineHandler.super.getTenantIdColumn());
        }
        return TenantLineHandler.super.getTenantIdColumn();
    }

    private static TableName findTableNameAnnotation(Class<?> clazz) {
        for (Class<?> c = clazz; c != null && c != Object.class; c = c.getSuperclass()) {
            TableName tn = c.getAnnotation(TableName.class);
            if (tn != null) {
                return tn;
            }
        }
        return null;
    }

    private static String resolveLogicalTableName(Class<?> clazz, TableName tableNameAnn) {
        if (StringUtils.isNotBlank(tableNameAnn.value())) {
            return tableNameAnn.value();
        }
        return StringUtils.camelToUnderline(clazz.getSimpleName());
    }

    private static String normalizeTableName(String tableName) {
        if (tableName == null) {
            return "";
        }
        String t = tableName.trim();
        if (t.isEmpty()) {
            return "";
        }
        int dot = t.lastIndexOf('.');
        if (dot >= 0 && dot < t.length() - 1) {
            t = t.substring(dot + 1);
        }
        t = t.replace("\"", "").replace("`", "");
        return t.toLowerCase(Locale.ROOT);
    }
}
