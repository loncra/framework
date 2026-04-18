package io.github.loncra.framework.wechat.domain.metadata.applet;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * 数据水印元数据
 *
 * @author maurice.chen
 */
public class WatermarkMetadata implements Serializable {
    @Serial
    private static final long serialVersionUID = -3010541299304006901L;

    /**
     * 用户获取手机号操作的时间戳
     */
    private Instant timestamp;

    /**
     * 小程序 appid
     */
    private String appId;

    /**
     * 构造函数
     */
    public WatermarkMetadata() {
    }

    /**
     * 获取用户获取手机号操作的时间戳
     *
     * @return 时间戳
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * 设置用户获取手机号操作的时间戳
     *
     * @param timestamp 时间戳
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取小程序 appid
     *
     * @return 小程序 appid
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置小程序 appid
     *
     * @param appId 小程序 appid
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }
}
