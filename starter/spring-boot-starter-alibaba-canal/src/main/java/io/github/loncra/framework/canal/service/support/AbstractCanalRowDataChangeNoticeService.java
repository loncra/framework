package io.github.loncra.framework.canal.service.support;

import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeAckMessage;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeNoticeResolver;
import io.github.loncra.framework.canal.service.CanalRowDataChangeNoticeService;
import io.github.loncra.framework.commons.exception.SystemException;

import java.util.List;

/**
 * 抽象的 canal 行数据变更通知实现
 *
 * @author maurice.chen
 */
public abstract class AbstractCanalRowDataChangeNoticeService implements CanalRowDataChangeNoticeService {

    private List<CanalRowDataChangeNoticeResolver> canalRowDataChangeNoticeResolvers;

    public AbstractCanalRowDataChangeNoticeService() {
    }

    public AbstractCanalRowDataChangeNoticeService(List<CanalRowDataChangeNoticeResolver> canalRowDataChangeNoticeResolvers) {
        this.canalRowDataChangeNoticeResolvers = canalRowDataChangeNoticeResolvers;
    }

    @Override
    public void sendAckMessage(CanalRowDataChangeAckMessage ackMessage) {
        canalRowDataChangeNoticeResolvers
                .stream()
                .filter(c -> c.isSupport(ackMessage))
                .findFirst()
                .orElseThrow(() -> new SystemException("找不到 [" + ackMessage + "] 的通知解析器支持"))
                .send(ackMessage, e -> this.saveAckMessage(ackMessage));

    }

    public List<CanalRowDataChangeNoticeResolver> getCanalRowDataChangeNoticeResolvers() {
        return canalRowDataChangeNoticeResolvers;
    }

    public void setCanalRowDataChangeNoticeResolvers(List<CanalRowDataChangeNoticeResolver> canalRowDataChangeNoticeResolvers) {
        this.canalRowDataChangeNoticeResolvers = canalRowDataChangeNoticeResolvers;
    }
}
