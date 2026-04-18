package io.github.loncra.framework.security.test.audit;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.security.audit.IdAuditEvent;
import io.github.loncra.framework.security.audit.elasticsearch.ElasticsearchAuditEventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 审计事件仓库单元测试
 *
 * @author maurice.chen
 */
@SpringBootTest
@ActiveProfiles("elasticsearch")
public class ElasticsearchAuditEventRepositoryTest {

    @Autowired
    private ElasticsearchAuditEventRepository auditEventRepository;

    @Test
    public void test() throws InterruptedException {
        Instant instant = Instant.now();

        auditEventRepository
                .getElasticsearchOperations()
                .indexOps(IndexCoordinates.of(auditEventRepository.getIndexName(instant)))
                .delete();

        int before = auditEventRepository.find("admin", instant, null).size();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("d", 1);
        map.put("xx", 3);
        map.put("test", "tests");

        Map<String, Object> date = new LinkedHashMap<>();
        date.put("date", instant);
        map.put("data", date);


        auditEventRepository.add(new IdAuditEvent("admin", "test", map));
        Thread.sleep(5000);
        List<AuditEvent> auditEvents = auditEventRepository.find("admin", instant, null);

        Assertions.assertEquals(before + 1, auditEvents.size());

        IdAuditEvent target = CastUtils.cast(auditEvents.getFirst());

        StringIdEntity id = new StringIdEntity();
        id.setId(target.getId());
        id.setCreationTime(instant);

        AuditEvent event = auditEventRepository.get(id);

        Assertions.assertEquals(event.getPrincipal(), target.getPrincipal());
        Assertions.assertEquals(event.getType(), target.getType());
        Assertions.assertEquals(event.getData().size(), target.getData().size());
        Assertions.assertEquals(event.getTimestamp(), target.getTimestamp());

    }

}
