package io.github.loncra.framework.socketio.api.metadata;

import io.github.loncra.framework.commons.RestResult;

import java.io.Serial;

/**
 * 单播 socket 消息对象
 *
 * @param <T> 发送的数据类型
 *
 * @author maurice.chen
 */
public class UnicastMessageMetadata<T> extends AbstractSocketMessageMetadata<T> {

    @Serial
    private static final long serialVersionUID = 6202588511755627871L;

    public static final String DEFAULT_TYPE = "unicast";

    /**
     * 设备唯一识别
     */
    private String deviceIdentified;

    public UnicastMessageMetadata() {
    }

    public UnicastMessageMetadata(String deviceIdentified) {
        this.deviceIdentified = deviceIdentified;
    }

    public static <T> UnicastMessageMetadata<T> of(
            String deviceIdentified,
            String event,
            T data
    ) {
        return of(deviceIdentified, event, RestResult.ofSuccess(data));
    }

    public static <T> UnicastMessageMetadata<T> of(
            String deviceIdentified,
            String event,
            RestResult<T> message
    ) {

        UnicastMessageMetadata<T> result = new UnicastMessageMetadata<>(deviceIdentified);

        result.setMessage(message);
        result.setEvent(event);

        return result;
    }

    @Override
    public String getType() {
        return DEFAULT_TYPE;
    }

    public String getDeviceIdentified() {
        return deviceIdentified;
    }

    public void setDeviceIdentified(String deviceIdentified) {
        this.deviceIdentified = deviceIdentified;
    }
}
