package io.github.loncra.framework.canal.resolver;

import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeAckMessage;

import java.util.function.Consumer;

/**
 * canal 行数据变更通知解析器
 *
 * @author maurice.chen
 */
public interface CanalRowDataChangeNoticeResolver {

    /**
     * 是否支持通知实体
     *
     * @param entity canal 行数据变更通知记录实体
     *
     * @return true 是，否则 false
     */
    boolean isSupport(CanalRowDataChangeAckMessage entity);

    /**
     * 发送通知
     *
     * @param entity   canal 行数据变更通知记录实体
     * @param consumer 当数据发送变更后回调此接口
     */
    void send(
            CanalRowDataChangeAckMessage entity,
            Consumer<CanalRowDataChangeAckMessage> consumer
    );
}
