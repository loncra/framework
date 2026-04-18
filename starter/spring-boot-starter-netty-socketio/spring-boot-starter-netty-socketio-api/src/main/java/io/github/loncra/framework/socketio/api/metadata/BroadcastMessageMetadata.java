package io.github.loncra.framework.socketio.api.metadata;

import io.github.loncra.framework.commons.RestResult;

import java.io.Serial;

/**
 * 广播 socket 消息对象
 *
 * @param <T> 发送的数据类型
 *
 * @author maurice
 */
public class BroadcastMessageMetadata<T> extends AbstractSocketMessageMetadata<T> {

    @Serial
    private static final long serialVersionUID = 2192204554091742380L;

    public static final String DEFAULT_TYPE = "broadcast";

    /**
     * 房间频道，如果为空全网广播
     */
    private String room;

    public BroadcastMessageMetadata() {
    }

    public BroadcastMessageMetadata(String room) {
        this.room = room;
    }

    public static <T> BroadcastMessageMetadata<T> of(
            String room,
            String event,
            T data
    ) {

        return of(room, event, RestResult.ofSuccess(data));
    }

    public static <T> BroadcastMessageMetadata<T> of(
            String event,
            T data
    ) {
        return of(event, RestResult.ofSuccess(data));
    }

    public static <T> BroadcastMessageMetadata<T> of(
            String room,
            String event,
            RestResult<T> message
    ) {

        BroadcastMessageMetadata<T> result = new BroadcastMessageMetadata<>(room);

        result.setMessage(message);
        result.setEvent(event);

        return result;
    }

    public static <T> BroadcastMessageMetadata<T> of(
            String event,
            RestResult<T> message
    ) {

        return of(null, event, message);
    }

    @Override
    public String getType() {
        return DEFAULT_TYPE;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
