package io.github.loncra.framework.captcha.tianai.config;

import cloud.tianai.captcha.resource.ResourceProvider;

import java.io.Serial;
import java.io.Serializable;

/**
 * 资源配置信息，用于 tianai 配置行为验证图片使用
 *
 * @author maurice.chen
 *
 */
public class ResourceProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = -1796848512858498745L;

    /**
     * 类型.
     */
    private String type;
    /**
     * 数据,传输给 {@link ResourceProvider} 的参数
     */
    public String data;
    /**
     * 标签.
     */
    private String tag;

    public ResourceProperties() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
