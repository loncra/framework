package io.github.loncra.framework.socketio.api.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.RestResult;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Socket 消息元数据基类。
 * <p>
 * 封装事件名、消息体和扩展元数据，供不同发送策略（单播/多播/广播）复用。
 *
 * @author maurice
 */
public abstract class AbstractSocketMessageMetadata<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 6021283894540422481L;
    /**
     * 事件类型
     */
    private String event;

    /**
     * 业务消息体（统一使用 RestResult 包装）。
     */
    private RestResult<T> message;

    /**
     * 扩展元数据（如 ACK 超时等运行时参数）。
     */
    private Map<String, Object> metadata = new LinkedHashMap<>();

    /**
     * 获取消息类型标识。
     *
     * @return 消息类型（如 unicast/broadcast/multipleUnicast）
     */
    @JsonIgnore
    public abstract String getType();

    public AbstractSocketMessageMetadata() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public RestResult<T> getMessage() {
        return message;
    }

    public void setMessage(RestResult<T> message) {
        this.message = message;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
