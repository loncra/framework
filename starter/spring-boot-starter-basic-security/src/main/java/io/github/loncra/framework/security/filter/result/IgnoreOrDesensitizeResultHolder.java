package io.github.loncra.framework.security.filter.result;

import io.github.loncra.framework.commons.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 忽略或脱敏结果持有者
 *
 * @author maurice.chen
 */
public class IgnoreOrDesensitizeResultHolder {

    /**
     * 线程本地模式
     */
    public static final String MODE_THREAD_LOCAL = "MODE_THREAD_LOCAL";

    /**
     * 系统属性名称
     */
    public static final String SYSTEM_PROPERTY = "ignore.or.desensitize.result.holder.strategy";

    /**
     * 策略名称
     */
    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);

    /**
     * 策略实现
     */
    private static IgnoreOrDesensitizeResultHolderStrategy strategy;

    /**
     * 初始化次数
     */
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

        // 如果没有配置策略名称，设置默认使用本地线程資源
        if (StringUtils.isBlank(strategyName)) {
            strategyName = MODE_THREAD_LOCAL;
        }

        // 如果策略名称为本地线程資源，创建本地线程資源
        if (strategyName.equals(MODE_THREAD_LOCAL)) {
            strategy = new ThreadLocalIgnoreOrDesensitizeResultHolderStrategy();
        }
        else {
            // 否则尝试去获取自定义的策略实现类
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor();
                strategy = (IgnoreOrDesensitizeResultHolderStrategy) customStrategy.newInstance();
            }
            catch (Exception ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
        }

        initializeCount++;
    }

    /**
     * 获取忽略对象的属性值
     *
     * @return 忽略对象的属性值
     */
    public static List<String> getIgnoreProperties() {
        List<String> strings = strategy.getIgnoreProperties();

        if (CollectionUtils.isEmpty(strings)) {
            strings = new ArrayList<>();
            strategy.setIgnoreProperties(strings);
        }

        return strings;
    }

    /**
     * 设置忽略对象的属性值
     *
     * @param value 忽略对象的属性值
     */
    public static void setIgnoreProperties(List<String> value) {
        strategy.setIgnoreProperties(value);
    }

    /**
     * 获取脱敏对象的属性值
     *
     * @return 脱敏对象的属性值
     */
    public static List<String> getDesensitizeProperties() {
        List<String> strings = strategy.getDesensitizeProperties();

        if (CollectionUtils.isEmpty(strings)) {
            strings = new ArrayList<>();
            strategy.setIgnoreProperties(strings);
        }

        return strings;
    }

    /**
     * 设置脱敏对象的属性值
     *
     * @param value 脱敏对象的属性值
     */
    public static void setDesensitizeProperties(List<String> value) {
        strategy.setDesensitizeProperties(value);
    }

    /**
     * 清除当前值
     */
    public static void clear() {
        strategy.clear();
    }


    /**
     * 设置策略名称
     *
     * @param strategyName 策略名称
     */
    public static void setStrategyName(String strategyName) {
        IgnoreOrDesensitizeResultHolder.strategyName = strategyName;
        initialize();
    }

    /**
     * 获取当前策略实现类
     *
     * @return 当前策略实现类
     */
    public static IgnoreOrDesensitizeResultHolderStrategy getContextHolderStrategy() {
        return strategy;
    }


    /**
     * 转换对象（忽略或脱敏指定字段）
     *
     * @param source 源对象
     *
     * @return 转换后的对象
     */
    public static Object convert(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }

        List<String> ignoreProperties = IgnoreOrDesensitizeResultHolder.getIgnoreProperties();
        if (CollectionUtils.isNotEmpty(ignoreProperties)) {
            source = ObjectUtils.ignoreObjectFieldToMap(source, ignoreProperties);
        }

        List<String> desensitizeProperties = IgnoreOrDesensitizeResultHolder.getDesensitizeProperties();
        if (CollectionUtils.isNotEmpty(desensitizeProperties)) {
            source = ObjectUtils.desensitizeObjectFieldToMap(source, desensitizeProperties);
        }

        return source;
    }
}
