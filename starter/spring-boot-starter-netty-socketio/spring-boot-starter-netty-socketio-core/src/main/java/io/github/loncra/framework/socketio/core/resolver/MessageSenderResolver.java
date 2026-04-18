package io.github.loncra.framework.socketio.core.resolver;

import com.corundumstudio.socketio.SocketIOServer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.ObjectUtils;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.security.filter.result.IgnoreOrDesensitizeResultHolder;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.core.SocketProperties;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Socket 消息发送解析器。
 * <p>
 * 负责判断“当前消息该由哪个发送器处理”，并将消息转换为可下发的 JSON 数据。
 *
 * @author maurice.chen
 */
public interface MessageSenderResolver {

    /**
     * 发送消息。
     *
     * @param socketMessage Socket 消息元数据
     * @param socketIoServer Socket 服务端实例
     */
    void sendMessage(AbstractSocketMessageMetadata<?> socketMessage, SocketIOServer socketIoServer);

    /**
     * 是否支持当前消息类型。
     *
     * @param socketMessage Socket 消息元数据
     * @return true 表示支持处理，否则不支持
     */
    boolean isSupport(AbstractSocketMessageMetadata<?> socketMessage);

    /**
     * 将消息转换为 JSON 字符串，并处理忽略和脱敏字段
     *
     * @param event 事件名称
     * @param message 消息对象
     * @param config Socket 配置属性
     * @return JSON 字符串
     */
    default String postMessageToJson(String event, Object message, SocketProperties config) {
        Map<String, Object> source = CastUtils.convertValue(message, CastUtils.MAP_TYPE_REFERENCE);
        List<String> ignoreProperties = config.getIgnoreResultMap().get(event);
        if (CollectionUtils.isNotEmpty(ignoreProperties)) {
            source = ObjectUtils.ignoreObjectFieldToMap(message, ignoreProperties);
        }

        List<String> desensitizeProperties = IgnoreOrDesensitizeResultHolder.getDesensitizeProperties();
        if (CollectionUtils.isNotEmpty(desensitizeProperties)) {
            source = ObjectUtils.desensitizeObjectFieldToMap(source, desensitizeProperties);
        }
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(source);

        return SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(linkedHashMap));
    }
}
