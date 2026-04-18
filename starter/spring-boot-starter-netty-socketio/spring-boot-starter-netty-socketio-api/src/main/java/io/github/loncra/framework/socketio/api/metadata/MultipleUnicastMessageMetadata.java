package io.github.loncra.framework.socketio.api.metadata;

import io.github.loncra.framework.commons.RestResult;

import java.io.Serial;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多目标单播消息元数据。
 * <p>
 * 适用于“同一份消息体发送给多个设备”的场景，
 * 发送时会按设备列表逐个下发（而非房间广播）。
 *
 * @param <T> 消息数据类型
 * @author maurice
 */
public class MultipleUnicastMessageMetadata<T> extends AbstractSocketMessageMetadata<T> {

    @Serial
    private static final long serialVersionUID = 6233942402525531392L;

    public static final String DEFAULT_TYPE = "multipleUnicast";

    /**
     * 目标设备标识列表。
     */
    private List<String> deviceIdentifiedList = new LinkedList<>();

    public MultipleUnicastMessageMetadata() {
    }

    public MultipleUnicastMessageMetadata(List<String> deviceIdentifiedList) {
        this.deviceIdentifiedList = deviceIdentifiedList;
    }

    /**
     * 转换为单播 socket 消息
     *
     * @return 单播 socket 消息集合
     */
    public List<UnicastMessageMetadata<?>> toUnicastMessageList() {
        return deviceIdentifiedList
                .stream()
                .map(c -> UnicastMessageMetadata.of(c, getEvent(), getMessage()))
                .collect(Collectors.toList());
    }

    public static <T> MultipleUnicastMessageMetadata<T> of(
            List<String> deviceIdentifiedList,
            String event,
            T data
    ) {

        return of(deviceIdentifiedList, event, RestResult.ofSuccess(data));
    }

    public static <T> MultipleUnicastMessageMetadata<T> of(
            List<String> deviceIdentifiedList,
            String event,
            RestResult<T> message
    ) {
        MultipleUnicastMessageMetadata<T> result = new MultipleUnicastMessageMetadata<>(deviceIdentifiedList);
        result.setEvent(event);
        result.setMessage(message);
        return result;
    }

    @Override
    public String getType() {
        return DEFAULT_TYPE;
    }

    public List<String> getDeviceIdentifiedList() {
        return deviceIdentifiedList;
    }

    public void setDeviceIdentifiedList(List<String> deviceIdentifiedList) {
        this.deviceIdentifiedList = deviceIdentifiedList;
    }
}
