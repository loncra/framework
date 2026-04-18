package io.github.loncra.framework.socketio.core.resolver.support;

import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.domain.body.AckResponseBody;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.api.metadata.MultipleUnicastMessageMetadata;
import io.github.loncra.framework.socketio.core.SocketProperties;
import io.github.loncra.framework.socketio.core.resolver.AckMessageSenderResolver;
import io.github.loncra.framework.socketio.core.resolver.MessageSenderResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 多目标单播消息发送器。
 * <p>
 * 将同一条消息按设备列表逐个发送，适用于“多人接收同一数据，但不走广播房间”的场景。
 *
 * @author maurice.chen
 */
public class MultipleUnicastMessageSender implements MessageSenderResolver, AckMessageSenderResolver<List<AckResponseBody<IdValueMetadata<String, Object>>>> {

    public static final Logger LOGGER = LoggerFactory.getLogger(MultipleUnicastMessageSender.class);

    private final SocketProperties config;

    public MultipleUnicastMessageSender(SocketProperties config) {
        this.config = config;
    }

    @Override
    public void sendMessage(
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketIOServer socketIoServer
    ) {
        MultipleUnicastMessageMetadata<?> message = CastUtils.cast(socketMessage);

        List<ClientOperations> clientOperations = message
                .getDeviceIdentifiedList()
                .stream()
                .map(c -> socketIoServer.getClient(UUID.fromString(c)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        String json = postMessageToJson(message.getEvent(), message.getMessage(), config);

        Assert.hasText(json, "推送消息不能为空");

        clientOperations.forEach(c -> AbstractMessageSenderResolver.sendEventMessage(c, socketMessage.getEvent(), json));
        if (!LOGGER.isDebugEnabled()) {
            return;
        }
        LOGGER.debug(
                "发送消息 [事件类型: {}, 数据: {}}] 到设备 [{}] 成功",
                message.getEvent(),
                json,
                message.getDeviceIdentifiedList()
        );
    }

    @Override
    public boolean isSupport(AbstractSocketMessageMetadata<?> socketMessage) {
        return MultipleUnicastMessageMetadata.class.isAssignableFrom(socketMessage.getClass());
    }

    @Override
    public List<AckResponseBody<IdValueMetadata<String, Object>>> ackSendMessage(
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketIOServer socketIoServer
    )  {

        MultipleUnicastMessageMetadata<?> message = CastUtils.cast(socketMessage);

        SystemException.isTrue(StringUtils.isNotEmpty(message.getEvent()), "推送消息事件不能为空");
        SystemException.isTrue(Objects.nonNull(message.getMessage()), "推送消息不能为空");

        List<CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>>> futures = message
                .getDeviceIdentifiedList()
                .stream()
                .map(c -> socketIoServer.getClient(UUID.fromString(c)))
                .filter(Objects::nonNull)
                .map(client -> this.createAckSendCompletableFuture(client, message, config))
                .toList();

        // 收集所有结果
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }


}
