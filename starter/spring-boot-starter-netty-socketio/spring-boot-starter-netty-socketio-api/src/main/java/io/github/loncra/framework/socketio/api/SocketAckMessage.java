package io.github.loncra.framework.socketio.api;

import io.github.loncra.framework.commons.domain.AbstractAckMessage;
import io.github.loncra.framework.commons.enumerate.basic.Protocol;

import java.io.Serial;
import java.util.Map;

/**
 * socket 确认消息实体
 *
 * @author maurice.chen
 */
public class SocketAckMessage extends AbstractAckMessage<Map<String, Object>> {

    @Serial
    private static final long serialVersionUID = 5899911975399222473L;

    @Override
    public Protocol getProtocol() {
        return Protocol.WS_OR_WSS;
    }
}
