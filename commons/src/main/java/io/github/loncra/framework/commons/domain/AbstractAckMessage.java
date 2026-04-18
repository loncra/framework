package io.github.loncra.framework.commons.domain;

import io.github.loncra.framework.commons.domain.body.AckResponseBody;

import java.io.Serial;
import java.util.Map;

/**
 * 抽象的 act 消息确认实现
 *
 * @author maurice.chen
 */
public abstract class AbstractAckMessage<T> implements AckMessage<T> {

    @Serial
    private static final long serialVersionUID = -8241650388223188355L;

    /**
     * 元数据信息
     */
    private Map<String, Object> metadata;

    /**
     * 请求体
     */
    private Map<String, Object> requestBody;

    /**
     * 响应体
     */
    private AckResponseBody<T> responseBody;

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public Map<String, Object> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Map<String, Object> requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public AckResponseBody<T> getResponseBody() {
        return responseBody;
    }

    @Override
    public void setResponseBody(AckResponseBody<T> responseBody) {
        this.responseBody = responseBody;
    }
}
