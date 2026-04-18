package io.github.loncra.framework.spring.security.core.authentication.config;

import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.security.plugin.PluginInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

/**
 * 插件配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.plugin")
public class PluginProperties {
    /**
     * 需要扫描的包路径
     */
    private List<String> basePackages = new ArrayList<>(16);

    /**
     * 生成插件的来源类型集合，用于通过该值去配合 {@link Plugin#sources()} 来判断构造 info 中的 plugin 时，否添加到 plugin 中
     */
    private List<String> generateSources = new LinkedList<>();

    /**
     * 找不到父类的插件信息
     */
    private Map<String, PluginInfo> parent = new LinkedHashMap<>();

    public PluginProperties() {
    }

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    public List<String> getGenerateSources() {
        return generateSources;
    }

    public void setGenerateSources(List<String> generateSources) {
        this.generateSources = generateSources;
    }

    public Map<String, PluginInfo> getParent() {
        return parent;
    }

    public void setParent(Map<String, PluginInfo> parent) {
        this.parent = parent;
    }
}
