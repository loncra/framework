package io.github.loncra.framework.allin.pay.domain.metadata;

import java.io.Serial;

/**
 * 基础版本请求元数据
 *
 * @author maurice.chen
 */
public class BasicVersionRequestMetadata extends BasicRequestMetadata {

    @Serial
    private static final long serialVersionUID = -5865907332619198826L;

    /**
     * 版本号
     */
    private String version = "v1.0";

    /**
     * 构造函数
     */
    public BasicVersionRequestMetadata() {
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
