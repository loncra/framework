package io.github.loncra.framework.canal.service.support;

import io.github.loncra.framework.canal.domain.CanalRowDataChangeNotice;
import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeAckMessage;
import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeNoticeEntity;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeNoticeResolver;
import io.github.loncra.framework.commons.CastUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 内存存储形式的 canal 行数据变更通知服务实现
 *
 * @author maurice.chen
 */
public class InMemoryCanalRowDataChangeNoticeService extends AbstractCanalRowDataChangeNoticeService {

    private static final List<CanalRowDataChangeNoticeEntity> CACHE_NOTICE_DATA = Collections.synchronizedList(new ArrayList<>());

    private static final List<CanalRowDataChangeAckMessage> CACHE_NOTICE_RECORD_DATA = Collections.synchronizedList(new ArrayList<>());

    public InMemoryCanalRowDataChangeNoticeService() {
    }

    public InMemoryCanalRowDataChangeNoticeService(List<CanalRowDataChangeNoticeResolver> canalRowDataChangeNoticeResolvers) {
        super(canalRowDataChangeNoticeResolvers);
    }

    @Override
    public List<CanalRowDataChangeNotice> findEnableByDestinations(List<String> destinations) {
        List<CanalRowDataChangeNoticeEntity> result = new LinkedList<>();
        for (String destination : destinations) {
            String databaseName = StringUtils.substringBefore(destination, CastUtils.COMMA);
            String tableName = StringUtils.substringAfter(destination, CastUtils.COMMA);
            if (result.stream().anyMatch(r -> r.getDatabaseName().equals(databaseName) && r.getTableName().equals(tableName))) {
                continue;
            }
            CACHE_NOTICE_DATA
                    .stream()
                    .filter(c -> c.getDatabaseName().equals(databaseName))
                    .filter(c -> c.getTableName().equals(tableName))
                    .forEach(result::add);
        }
        return new LinkedList<>(result);
    }

    @Override
    public CanalRowDataChangeAckMessage saveAckMessage(CanalRowDataChangeAckMessage record) {
        CACHE_NOTICE_RECORD_DATA.add(record);
        return record;
    }
}
