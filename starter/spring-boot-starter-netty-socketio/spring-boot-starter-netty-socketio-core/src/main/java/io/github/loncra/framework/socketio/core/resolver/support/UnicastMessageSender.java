package io.github.loncra.framework.socketio.core.resolver.support;

import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.domain.body.AckResponseBody;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.api.metadata.UnicastMessageMetadata;
import io.github.loncra.framework.socketio.core.resolver.AckMessageSenderResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 单播消息发送器。
 * <p>
 * 按设备唯一标识定位单个客户端并发送消息，可选择 ACK 模式等待客户端应答。
 *
 * @author maurice.chen
 */
public class UnicastMessageSender extends AbstractMessageSenderResolver<UnicastMessageMetadata<?>> implements AckMessageSenderResolver<AckResponseBody<IdValueMetadata<String, Object>>> {

    public static final Logger LOGGER = LoggerFactory.getLogger(UnicastMessageSender.class);

    @Override
    public boolean isSupport(AbstractSocketMessageMetadata<?> socketMessage) {
        return UnicastMessageMetadata.class.isAssignableFrom(socketMessage.getClass());
    }

    @Override
    protected ClientOperations getClientOperations(
            UnicastMessageMetadata<?> message,
            SocketIOServer socketIoServer
    ) {

        String deviceIdentified = message.getDeviceIdentified();

        if (StringUtils.isNotBlank(deviceIdentified)) {
            return socketIoServer.getClient(UUID.fromString(deviceIdentified));
        }

        return null;
    }

    @Override
    public AckResponseBody<IdValueMetadata<String, Object>> ackSendMessage(
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketIOServer socketIoServer
    ) throws ExecutionException, InterruptedException {
        Assert.hasText(socketMessage.getEvent(), "推送事件不能为空");

        UnicastMessageMetadata<?> message = CastUtils.cast(socketMessage);
        ClientOperations clientOperations = getClientOperations(message, socketIoServer);
        if (clientOperations instanceof SocketIOClient socketIoClient) {
            CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>> future = createAckSendCompletableFuture(socketIoClient, socketMessage, getConfig());
            return future.get();
        }
        throw new SystemException("ClientOperations 并非 SocketIOClient 实现，无法完成应答发送消息");
    }

    @Override
    protected void afterSetting(
            ClientOperations clientOperations,
            UnicastMessageMetadata<?> message,
            String json
    ) {
        if (!LOGGER.isDebugEnabled()) {
            return;
        }
        LOGGER.debug(
                "发送消息 [事件类型: {}, 数据: {}}] 到设备 [{}] 成功",
                message.getEvent(),
                json,
                message.getDeviceIdentified()
        );
    }
}
