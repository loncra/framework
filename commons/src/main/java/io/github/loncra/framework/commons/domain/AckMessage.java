package io.github.loncra.framework.commons.domain;

import io.github.loncra.framework.commons.domain.body.AckResponseBody;
import io.github.loncra.framework.commons.domain.metadata.ProtocolMetadata;

import java.io.Serializable;
import java.util.Map;

/**
 * 确认消息接口，用于发送指定协议的消息信息并根据响应结果来确定是否需要重新发送消息使用
 *
 * @author maurice.chen
 */
public interface AckMessage<T> extends Serializable, ProtocolMetadata {

    /**
     * 获取消息请求体
     *
     * @return 消息请求体
     */
    Map<String, Object> getRequestBody();

    /**
     * 获取 ack 响应体
     *
     * @return ack 响应体
     */
    AckResponseBody<T> getResponseBody();

    /**
     * 设置 ack 响应体
     *
     * @param ackResponseBody ack 响应体
     */
    void setResponseBody(AckResponseBody<T> ackResponseBody);
}
