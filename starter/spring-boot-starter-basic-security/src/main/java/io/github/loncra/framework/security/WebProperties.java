package io.github.loncra.framework.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * web 安全配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.security.web")
public class WebProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 2883044237396956726L;

    /**
     * 请求返回结果时要忽略的字段内容
     */
    private Map<String, List<String>> ignoreResultMap = new LinkedHashMap<>();

    /**
     * 请求返回结果时要加 * 的数据内容映射
     */
    private Map<String, List<String>> desensitizeResultMap = new LinkedHashMap<>();

    public WebProperties() {
    }

    /**
     * 获取请求返回结果时要忽略的字段内容
     *
     * @return 请求返回结果时要忽略的字段内容
     */
    public Map<String, List<String>> getIgnoreResultMap() {
        return ignoreResultMap;
    }

    /**
     * 设置请求返回结果时要忽略的字段内容
     *
     * @param ignoreResultMap 请求返回结果时要忽略的字段内容
     */
    public void setIgnoreResultMap(Map<String, List<String>> ignoreResultMap) {
        this.ignoreResultMap = ignoreResultMap;
    }

    /**
     * 获取请求返回结果时要加 * 的数据内容映射
     *
     * @return 请求返回结果时要加 * 的数据内容映射
     */
    public Map<String, List<String>> getDesensitizeResultMap() {
        return desensitizeResultMap;
    }

    /**
     * 设置请求返回结果时要加 * 的数据内容映射
     *
     * @param desensitizeResultMap 请求返回结果时要加 * 的数据内容映射
     */
    public void setDesensitizeResultMap(Map<String, List<String>> desensitizeResultMap) {
        this.desensitizeResultMap = desensitizeResultMap;
    }
}
