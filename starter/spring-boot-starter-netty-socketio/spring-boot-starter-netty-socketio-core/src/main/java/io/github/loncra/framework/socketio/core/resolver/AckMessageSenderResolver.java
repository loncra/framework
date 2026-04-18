package io.github.loncra.framework.socketio.core.resolver;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.domain.body.AckResponseBody;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.core.SocketProperties;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 带 ACK 回执能力的 Socket 消息发送解析器。
 * <p>
 * 在普通消息发送解析器基础上，定义了发送后需要客户端确认（ACK）的发送契约，
 * 并提供了创建 ACK 异步结果 {@link CompletableFuture} 的默认实现。
 * 默认实现会根据消息元数据或全局配置计算 ACK 超时时间，并使用异步回调接收客户端应答。
 *
 * @param <T> ACK 发送结果类型（通常为统一应答体或其包装类型）
 * @author maurice.chen
 */
public interface AckMessageSenderResolver<T> extends MessageSenderResolver {

    /**
     * 发送带 ACK 确认的消息。
     *
     * @param socketMessage 待发送的 Socket 消息元数据
     * @param socketIoServer Socket 服务端实例
     *
     * @return ACK 应答结果
     * @throws ExecutionException 等待 ACK 结果时发生执行异常
     * @throws InterruptedException 当前线程在等待 ACK 结果期间被中断
     */
    T ackSendMessage(
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketIOServer socketIoServer
    ) throws ExecutionException, InterruptedException;

    /**
     * 创建 ACK 发送对应的异步结果。
     * <p>
     * 该方法会向指定客户端发送事件，并注册 ACK 回调；当客户端返回 ACK 或超时后，
     * 返回的 {@link CompletableFuture} 将完成（成功或异常完成由回调实现决定）。
     * <p>
     * ACK 超时时间读取规则如下：
     * 先从 {@code socketMessage.getMetadata()} 中读取 {@link TimeProperties}，
     * 若不存在则使用 {@link SocketProperties#getAckSendMessageTimeout()}，
     * 并将结果写回消息元数据，避免重复计算。
     *
     * @param client 目标 Socket 客户端
     * @param socketMessage 待发送的消息元数据（包含事件名、消息体及扩展元信息）
     * @param config Socket 全局配置，用于序列化与默认 ACK 超时配置
     *
     * @return ACK 应答异步结果，结果类型为 {@link AckResponseBody}
     */
    default CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>> createAckSendCompletableFuture(
            SocketIOClient client,
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketProperties config
    ) {

        Object timeoutObject = socketMessage.getMetadata()
                .computeIfAbsent(TimeProperties.class.getName(), k -> config.getAckSendMessageTimeout());
        TimeProperties timeout = CastUtils.cast(timeoutObject);

        String json = postMessageToJson(socketMessage.getEvent(), socketMessage.getMessage(), config);
        ClientAckCallback callback = new ClientAckCallback(Object.class, Objects.toString(client.getSessionId()), (int)timeout.toSeconds());
        client.sendEvent(socketMessage.getEvent(), callback, json);

        return callback.getFuture();
    }

    /**
     * 为多播场景创建 ACK 异步结果集合。
     * <p>
     * 该方法会遍历当前多播操作中的所有客户端，
     * 为每个客户端分别调用单播 ACK 创建逻辑，返回一个与客户端一一对应的
     * {@link CompletableFuture} 列表。
     * <p>
     * 返回结果仅负责提供各客户端 ACK 的异步句柄，不在此处阻塞等待；
     * 调用方可按需选择串行/并行聚合、超时控制及异常处理策略。
     *
     * @param broadcastOperations 多播客户端操作对象（包含当前可达的客户端集合）
     * @param socketMessage 待发送的消息元数据（包含事件名、消息体及扩展元信息）
     * @param config Socket 全局配置，用于序列化与默认 ACK 超时配置
     * @return 多播 ACK 异步结果集合；若无可用客户端，则返回空列表
     */
    default List<CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>>> createAckSendCompletableFuture(
            BroadcastOperations broadcastOperations,
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketProperties config
    ) {
        return broadcastOperations.getClients()
                .stream()
                .filter(Objects::nonNull)
                .map(client -> this.createAckSendCompletableFuture(client, socketMessage, config))
                .toList();
    }
}
