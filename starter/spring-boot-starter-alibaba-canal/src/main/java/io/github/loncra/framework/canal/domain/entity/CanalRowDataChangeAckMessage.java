package io.github.loncra.framework.canal.domain.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.loncra.framework.canal.domain.CanalMessage;
import io.github.loncra.framework.canal.domain.meta.HttpCanalRowDataChangeNoticeMetadata;
import io.github.loncra.framework.canal.service.CanalRowDataChangeNoticeService;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.domain.AbstractAckMessage;
import io.github.loncra.framework.commons.domain.metadata.ProtocolMetadata;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.enumerate.basic.Protocol;
import io.github.loncra.framework.commons.retry.Retryable;

import java.io.Serial;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * canal 行数据变更通知记录实体
 *
 * @author maurice.chen
 */
public class CanalRowDataChangeAckMessage extends AbstractAckMessage<Map<String, Object>> implements ExecuteStatus.Body, Retryable {

    @Serial
    private static final long serialVersionUID = 7867580272505563609L;

    /**
     * 通知类型
     */
    private Protocol protocol;

    /**
     * 响应时间
     */
    private Instant successTime;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 执行状态
     */
    private ExecuteStatus executeStatus = ExecuteStatus.Processing;

    /**
     * 重试次数
     */
    private Integer retryCount = 0;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCount = 3;

    /**
     * 最后发送时间
     */
    private Instant retryTime;

    public CanalRowDataChangeAckMessage() {

    }

    public static List<CanalRowDataChangeAckMessage> of(
            ProtocolMetadata notice,
            Map<String, Object> body
    ) {
        List<CanalRowDataChangeAckMessage> result = new ArrayList<>();
        if (Protocol.HTTP_OR_HTTPS.equals(notice.getProtocol())) {

            List<HttpCanalRowDataChangeNoticeMetadata> metas = CastUtils.convertValue(
                    notice.getMetadata().get(CanalRowDataChangeNoticeService.HTTP_ENTITY_FIELD),
                    new TypeReference<>() {
                    }
            );
            for (HttpCanalRowDataChangeNoticeMetadata map : metas) {
                CanalRowDataChangeAckMessage entity = CastUtils.of(
                        notice,
                        CanalRowDataChangeAckMessage.class,
                        CanalRowDataChangeNoticeEntity.PROTOCOL_META_FIELD_NAME
                );
                if (Objects.isNull(entity)) {
                    continue;
                }
                entity.setMetadata(CastUtils.convertValue(map, CastUtils.MAP_TYPE_REFERENCE));
                entity.setRequestBody(body);
                result.add(entity);
            }
        }
        else {
            CanalRowDataChangeAckMessage entity = CastUtils.of(
                    notice,
                    CanalRowDataChangeAckMessage.class,
                    CanalRowDataChangeNoticeEntity.PROTOCOL_META_FIELD_NAME
            );

            if (Objects.nonNull(entity)) {
                entity.setRequestBody(body);
                result.add(entity);
            }
        }
        return result;
    }

    public static List<CanalRowDataChangeAckMessage> of(
            ProtocolMetadata meta,
            CanalMessage message
    ) {
        return of(meta, CastUtils.convertValue(message, CastUtils.MAP_TYPE_REFERENCE));
    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    @Override
    public Instant getSuccessTime() {
        return successTime;
    }

    @Override
    public void setSuccessTime(Instant successTime) {
        this.successTime = successTime;
    }

    @Override
    public String getException() {
        return exception;
    }

    @Override
    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public ExecuteStatus getExecuteStatus() {
        return executeStatus;
    }

    @Override
    public void setExecuteStatus(ExecuteStatus executeStatus) {
        this.executeStatus = executeStatus;
    }

    @Override
    public Integer getRetryCount() {
        return retryCount;
    }

    @Override
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

    @Override
    public void setMaxRetryCount(Integer maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public Instant getRetryTime() {
        return retryTime;
    }

    @Override
    public void setRetryTime(Instant retryTime) {
        this.retryTime = retryTime;
    }
}
