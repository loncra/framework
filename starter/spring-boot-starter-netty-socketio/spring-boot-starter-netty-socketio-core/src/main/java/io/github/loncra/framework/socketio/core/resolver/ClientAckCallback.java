package io.github.loncra.framework.socketio.core.resolver;

import com.corundumstudio.socketio.AckCallback;
import io.github.loncra.framework.commons.domain.body.AckResponseBody;
import io.github.loncra.framework.commons.enumerate.basic.AckStatus;
import io.github.loncra.framework.commons.id.metadata.IdValueMetadata;

import java.util.concurrent.CompletableFuture;

/**
 * 客户端应答回调实现
 *
 * @author maurice.chen
 */
public class ClientAckCallback extends AckCallback<Object> {
    private final String deviceIdentified;
    private final CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>> future = new CompletableFuture<>();

    public ClientAckCallback(
            Class<Object> resultClass,
            String deviceIdentified
    ) {
        this(resultClass, deviceIdentified, 10);
    }

    public ClientAckCallback(
            Class<Object> resultClass,
            String deviceIdentified,
            int timeout
    ) {
        super(resultClass, timeout);
        this.deviceIdentified = deviceIdentified;
    }

    @Override
    public void onSuccess(Object result) {
        AckResponseBody<IdValueMetadata<String, Object>> ackResponseBody = new AckResponseBody<>();
        ackResponseBody.setAck(AckStatus.ACKNOWLEDGED);
        ackResponseBody.setMessage(IdValueMetadata.of(deviceIdentified, result));
        future.complete(ackResponseBody);
    }

    @Override
    public void onTimeout() {
        super.onTimeout();
        AckResponseBody<IdValueMetadata<String, Object>> ackResponseBody = new AckResponseBody<>();
        ackResponseBody.setAck(AckStatus.NEGLECT);
        ackResponseBody.setMessage(IdValueMetadata.of(deviceIdentified, null));
        future.complete(ackResponseBody);
    }

    public CompletableFuture<AckResponseBody<IdValueMetadata<String, Object>>> getFuture() {
        return future;
    }

}
