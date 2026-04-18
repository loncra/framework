package io.github.loncra.framework.socketio.api;

import java.io.Serial;
import java.io.Serializable;

/**
 * Socket 响应载荷，客户端需按此格式回传
 * <p>
 * 客户端约定：{@code socket.emit(eventName, { requestId, data: result })}
 *
 * @author maurice.chen
 */
public class SocketResponsePayload implements Serializable {

    @Serial
    private static final long serialVersionUID = -8912345678901234567L;

    /**
     * 请求 ID，与服务端发送时注入的 requestId 对应
     */
    private String requestId;

    /**
     * 响应数据
     */
    private Object data;

    public SocketResponsePayload() {
    }

    public SocketResponsePayload(String requestId, Object data) {
        this.requestId = requestId;
        this.data = data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
