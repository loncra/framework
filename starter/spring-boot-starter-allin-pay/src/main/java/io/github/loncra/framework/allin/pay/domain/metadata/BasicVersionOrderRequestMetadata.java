package io.github.loncra.framework.allin.pay.domain.metadata;

import java.io.Serial;

/**
 * 基础版本订单请求元数据
 *
 * @author maurice.chen
 */
public class BasicVersionOrderRequestMetadata extends BasicOrderRequestMetadata {

    @Serial
    private static final long serialVersionUID = -1680898755400650022L;

    /**
     * 版本号
     */
    private String version;

    /**
     * 构造函数
     */
    public BasicVersionOrderRequestMetadata() {
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
