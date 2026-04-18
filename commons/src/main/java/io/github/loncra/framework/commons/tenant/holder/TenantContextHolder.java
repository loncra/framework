package io.github.loncra.framework.commons.tenant.holder;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.holder.strategy.ThreadLocalTenantContextHolderStrategy;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;

/**
 * 租户上下文
 *
 * @author maurice.chen
 */
public class TenantContextHolder {

    public static final String MODE_THREAD_LOCAL = "MODE_THREAD_LOCAL";

    public static final String SYSTEM_PROPERTY = "loncra.framework.tenant.holder.strategy";

    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);

    private static TenantContextHolderStrategy strategy;

    private static int initializeCount = 0;

    static {
        // 初始化
        initialize();
    }

    /**
     * 获取初始化次数
     *
     * @return 初始化次数
     */
    public static int getInitializeCount() {
        return initializeCount;
    }

    /**
     * 初始化策略实现类
     */
    private static void initialize() {

        // 如果没有配置策略名称，默认使用 ThreadLocal 策略
        if (!StringUtils.hasText(strategyName)) {
            strategyName = MODE_THREAD_LOCAL;
        }

        // 如果策略名称为 ThreadLocal，创建对应策略实现
        if (strategyName.equals(MODE_THREAD_LOCAL)) {
            strategy = new ThreadLocalTenantContextHolderStrategy();
        }
        else {
            // 否则尝试去获取自定义的策略实现类
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor();
                strategy = CastUtils.cast(customStrategy.newInstance());
            }
            catch (Exception ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
        }

        initializeCount++;
    }

    /**
     * 获取当前租户上下文
     *
     * @return 当前租户上下文
     */
    public static SimpleTenantContext get() {
        return strategy.get();
    }

    /**
     * 清除当前租户上下文
     */
    public static void clear() {
        strategy.clear();
    }

    /**
     * 设置当前租户上下文
     *
     * @param tenantContext 租户上下文
     */
    public static void set(SimpleTenantContext tenantContext) {
        strategy.set(tenantContext);
    }

    /**
     * 创建当前租户上下文
     *
     * @return 租户上下文
     */
    public static SimpleTenantContext create() {
        return strategy.create();
    }

    /**
     * 设置策略名称
     *
     * @param strategyName 策略名称
     */
    public static void setStrategyName(String strategyName) {
        TenantContextHolder.strategyName = strategyName;
        initialize();
    }

    /**
     * 获取当前策略实现类
     *
     * @return 当前策略实现类
     */
    public static TenantContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }
}
