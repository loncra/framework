package io.github.loncra.framework.socketio.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.socketio.api.metadata.UnicastMessageMetadata;
import org.springframework.beans.BeanUtils;

import java.io.Serial;

/**
 * 单播 socket 用户消息对象, 该对象和 {@link UnicastMessageMetadata} 区别在于:本对象能直接知道 socket 服务器的具体 ip，可以直接发送。
 * 而 {@link UnicastMessageMetadata}
 * 不管该设备识别在不在当前服务器。
 *
 * @param <T> 消息数据类型
 *
 * @author maurice.chen
 */
public class SocketUserMessage<T> extends UnicastMessageMetadata<T> {

    @Serial
    private static final long serialVersionUID = 5389291016612742370L;

    /**
     * 设备唯一识别
     */
    @JsonIgnore
    private SocketPrincipal principal;

    public SocketPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(SocketPrincipal principal) {
        this.principal = principal;
    }

    public static <T> SocketUserMessage<T> of(
            SocketPrincipal details,
            String event,
            RestResult<T> message
    ) {

        UnicastMessageMetadata<T> source = of(details.getDeviceIdentified(), event, message);

        SocketUserMessage<T> target = new SocketUserMessage<>();
        BeanUtils.copyProperties(source, target);
        target.setPrincipal(details);

        return target;
    }

    public static <T> SocketUserMessage<T> of(
            SocketPrincipal details,
            String event,
            T data
    ) {

        return of(details, event, RestResult.ofSuccess(data));
    }

}
