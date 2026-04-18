package io.github.loncra.framework.security;

import io.github.loncra.framework.commons.CastUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 审计索引配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.security.audit.storage-position")
public class StoragePositionProperties implements Serializable {

    /**
     * 默认日期格式模式（已废弃）
     */
    @Deprecated
    public static final String DEFAULT_PATTERN = "yyyy_MM_dd";

    /**
     * 前缀
     */
    private String prefix = "audit_event";

    /**
     * 分隔符
     */
    private String separator = CastUtils.UNDERSCORE;

    /**
     * spring el 表达式
     */
    private List<String> springExpression = new LinkedList<>();

    /**
     * 构造函数
     */
    public StoragePositionProperties() {
    }

    /**
     * 获取前缀
     *
     * @return 前缀
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 设置前缀
     *
     * @param prefix 前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取分隔符
     *
     * @return 分隔符
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * 设置分隔符
     *
     * @param separator 分隔符
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * 获取 Spring EL 表达式列表
     *
     * @return Spring EL 表达式列表
     */
    public List<String> getSpringExpression() {
        return springExpression;
    }

    /**
     * 设置 Spring EL 表达式列表
     *
     * @param springExpression Spring EL 表达式列表
     */
    public void setSpringExpression(List<String> springExpression) {
        this.springExpression = springExpression;
    }
}
