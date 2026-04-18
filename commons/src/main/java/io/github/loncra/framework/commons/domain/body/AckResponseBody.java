package io.github.loncra.framework.commons.domain.body;

import io.github.loncra.framework.commons.enumerate.basic.AckStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * ack 确认响应题
 *
 * @author maurice.chen
 */
public class AckResponseBody<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -504725054089049434L;

    /**
     * 确认信息
     */
    private AckStatus ack = AckStatus.UNKNOWN;

    /**
     *
     */
    private T message;

    public AckResponseBody() {

    }

    public AckResponseBody(AckStatus ack) {
        this.ack = ack;
    }

    public AckResponseBody(
            AckStatus ack,
            T message
    ) {
        this.ack = ack;
        this.message = message;
    }

    public AckStatus getAck() {
        return ack;
    }

    public void setAck(AckStatus ack) {
        this.ack = ack;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AckResponseBody{" +
                "ack=" + ack +
                ", message=" + message +
                '}';
    }
}
