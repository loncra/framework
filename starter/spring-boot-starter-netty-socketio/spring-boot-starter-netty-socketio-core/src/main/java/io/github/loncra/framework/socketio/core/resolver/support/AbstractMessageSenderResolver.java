package io.github.loncra.framework.socketio.core.resolver.support;

import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.core.SocketProperties;
import io.github.loncra.framework.socketio.core.SocketServerManager;
import io.github.loncra.framework.socketio.core.resolver.MessageSenderResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Socket 消息发送解析器抽象基类。
 * <p>
 * 子类只需要实现“如何找到目标客户端（或房间）”的逻辑，
 * 其余通用流程（参数校验、序列化、发送、发送后回调）由本类统一处理。
 *
 * @param <T> 具体的消息元数据类型
 * @author maurice.chen
 */
public abstract class AbstractMessageSenderResolver<T extends AbstractSocketMessageMetadata<?>> implements MessageSenderResolver {

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageSenderResolver.class);

    private SocketProperties config;

    @Autowired(required = false)
    public void setConfig(SocketProperties config) {
        this.config = config;
    }

    public SocketProperties getConfig() {
        return config;
    }

    @Override
    public void sendMessage(
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketIOServer socketIoServer
    ) {

        Assert.hasText(socketMessage.getEvent(), "推送事件不能为空");

        T message = CastUtils.cast(socketMessage);

        ClientOperations clientOperations = getClientOperations(message, socketIoServer);

        if (Objects.isNull(clientOperations)) {
            LOGGER.warn("找不到 [{}] 的客户端操作", socketIoServer);
            return;
        }

        String json = postMessageToJson(message.getEvent(), message.getMessage(), config);
        SystemException.isTrue(StringUtils.isNotEmpty(json), "推送消息不能为空");
        sendEventMessage(clientOperations, message.getEvent(), json);
        afterSetting(clientOperations, message, json);
    }

    /**
     * 发送完成后的扩展回调。
     * <p>
     * 子类可按需覆盖该方法实现日志记录、埋点统计等后置逻辑。
     *
     * @param clientOperations 当前客户端操作对象
     * @param message 当前发送的消息元数据
     * @param json 已序列化的消息体 JSON 字符串
     */
    protected void afterSetting(
            ClientOperations clientOperations,
            T message,
            String json
    ) {

    }

    /**
     * 根据消息元数据获取目标客户端操作对象。
     *
     * @param message 当前消息元数据
     * @param socketIoServer Socket 服务端实例
     * @return 客户端操作对象（可为单播客户端或广播操作对象）
     */
    protected abstract ClientOperations getClientOperations(
            T message,
            SocketIOServer socketIoServer
    );

    /**
     * 发送事件消息。
     * <p>
     * 当事件为服务端主动断开事件时，会在发送通知后主动断开连接。
     *
     * @param client 客户端操作对象
     * @param event 事件名称
     * @param message 消息 JSON 字符串
     */
    public static void sendEventMessage(
            ClientOperations client,
            String event,
            String message
    ) {
        client.sendEvent(event, message);

        if (SocketServerManager.SERVER_DISCONNECT_EVENT_NAME.equals(event)) {
            client.disconnect();
        }
    }

}
