package io.github.loncra.framework.security.filter.result;

import java.util.List;

/**
 * 值持有者策略
 *
 * @author maurice.chen
 */
public interface IgnoreOrDesensitizeResultHolderStrategy {

    /**
     * 清除值
     */
    void clear();

    /**
     * 获取忽略对象的属性值
     *
     * @return 忽略对象的属性值
     */
    List<String> getIgnoreProperties();

    /**
     * 设置忽略对象的属性值
     *
     * @param values 忽略对象的属性值
     */
    void setIgnoreProperties(List<String> values);

    /**
     * 获取脱敏对象的属性值
     *
     * @return 脱敏对象的属性值
     */
    List<String> getDesensitizeProperties();

    /**
     * 设置脱敏对象的属性值
     *
     * @param values 脱敏对象的属性值
     */
    void setDesensitizeProperties(List<String> values);
}
