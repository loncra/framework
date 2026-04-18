package io.github.loncra.framework.socketio.core.resolver.support;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.namespace.Namespace;
import io.github.loncra.framework.commons.domain.body.AckResponseBody;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;
import io.github.loncra.framework.socketio.api.metadata.AbstractSocketMessageMetadata;
import io.github.loncra.framework.socketio.api.metadata.BroadcastMessageMetadata;
import io.github.loncra.framework.socketio.core.resolver.AckMessageSenderResolver;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 广播消息发送器。
 * <p>
 * 当消息指定 room 时发送到对应房间；未指定时发送到默认命名空间下的所有连接。
 *
 * @author maurice.chen
 */
public class BroadcastMessageSenderResolver extends AbstractMessageSenderResolver<BroadcastMessageMetadata<?>> implements AckMessageSenderResolver<List<AckResponseBody<IdValueMetadata<String, Object>>>> {

    @Override
    protected ClientOperations getClientOperations(
            BroadcastMessageMetadata<?> message,
            SocketIOServer socketIoServer
    ) {

        BroadcastOperations broadcastOperations = socketIoServer.getRoomOperations(Namespace.DEFAULT_NAME);

        String room = message.getRoom();

        if (StringUtils.isNotBlank(room)) {
            broadcastOperations = socketIoServer.getRoomOperations(room);
        }

        return broadcastOperations;
    }

    @Override
    public boolean isSupport(AbstractSocketMessageMetadata<?> socketMessage) {
        return BroadcastMessageMetadata.class.isAssignableFrom(socketMessage.getClass());
    }

    @Override
    public List<AckResponseBody<IdValueMetadata<String, Object>>> ackSendMessage(
            AbstractSocketMessageMetadata<?> socketMessage,
            SocketIOServer socketIoServer
    ) {
        BroadcastOperations broadcastOperations = socketIoServer.getRoomOperations(Namespace.DEFAULT_NAME);
        List<CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>>> futures = createAckSendCompletableFuture(broadcastOperations, socketMessage, getConfig());
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
