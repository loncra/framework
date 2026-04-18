package io.github.loncra.framework.mybatis.plus.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.basic.DisabledOrEnabled;
import io.github.loncra.framework.commons.enumerate.basic.ExecuteStatus;
import io.github.loncra.framework.commons.id.IdEntity;
import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.mybatis.enumerate.OperationDataType;
import io.github.loncra.framework.mybatis.interceptor.audit.OperationDataTraceRecord;
import io.github.loncra.framework.mybatis.plus.test.entity.AllTypeEntity;
import io.github.loncra.framework.mybatis.plus.test.service.AllTypeEntityService;
import org.apache.commons.lang3.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BasicServiceTest {

    @Autowired
    private AllTypeEntityService allTypeEntityService;

    @Autowired
    private AuditEventRepository auditEventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        CastUtils.setObjectMapper(objectMapper);
    }

    @Test
    public void testAllType() {

        AllTypeEntity entity = allTypeEntityService.get(1);

        Assertions.assertEquals(DisabledOrEnabled.Enabled, entity.getStatus());
        Assertions.assertEquals(2, entity.getDevice().size());

        Assertions.assertTrue(
                entity
                        .getEntities()
                        .stream()
                        .allMatch(c -> StringIdEntity.class.isAssignableFrom(c.getClass()))
        );

        Assertions.assertTrue(
                entity
                        .getExecutes()
                        .containsAll(Arrays.asList(ExecuteStatus.Processing, ExecuteStatus.Success, ExecuteStatus.Retrying))
        );

        Assertions.assertEquals(DisabledOrEnabled.Enabled, entity.getStatus());
    }

    @Test
    public void testInsertOrUpdate() throws Exception {
        AllTypeEntity entity = new AllTypeEntity();

        entity.setStatus(DisabledOrEnabled.Disabled);
        entity.setExecutes(Arrays.asList(ExecuteStatus.Failure, ExecuteStatus.Processing));

        allTypeEntityService.save(entity);

        List<AuditEvent> events = auditEventRepository.find(null, null, null);
        Assertions.assertEquals(1, events.size());

        AuditEvent event = events.iterator().next();
        Map<String, Object> submitData = CastUtils.cast(event.getData().get(OperationDataTraceRecord.SUBMIT_DATA_FIELD));

        Assertions.assertEquals(event.getPrincipal(), InetAddress.getLocalHost().getHostAddress());
        Assertions.assertEquals(submitData.get(IdEntity.ID_FIELD_NAME), entity.getId());
        Assertions.assertTrue(Strings.CS.endsWith(event.getType(), OperationDataType.INSERT.toString()));

        Assertions.assertEquals(objectMapper.writeValueAsString(submitData.get("status")), objectMapper.writeValueAsString(DisabledOrEnabled.Disabled));

        allTypeEntityService
                .lambdaUpdate()
                .set(AllTypeEntity::getStatus, DisabledOrEnabled.Enabled.getValue())
                .eq(AllTypeEntity::getId, entity.getId())
                .update();

        events = auditEventRepository.find(null, null, null);
        Assertions.assertEquals(2, events.size());

        event = events.getLast();
        Assertions.assertEquals(event.getPrincipal(), InetAddress.getLocalHost().getHostAddress());
        Assertions.assertTrue(Strings.CS.endsWith(event.getType(), OperationDataType.UPDATE.toString()));

        submitData = CastUtils.cast(event.getData().get(OperationDataTraceRecord.SUBMIT_DATA_FIELD));
        Integer statusValue = CastUtils.cast(submitData.get("status"));
        Assertions.assertEquals(statusValue, DisabledOrEnabled.Enabled.getValue());

        entity.setStatus(DisabledOrEnabled.Disabled);
        allTypeEntityService.updateById(entity);
        events = auditEventRepository.find(null, null, null);
        Assertions.assertEquals(3, events.size());

        event = events.getLast();
        Assertions.assertEquals(event.getPrincipal(), InetAddress.getLocalHost().getHostAddress());
        Assertions.assertTrue(Strings.CS.endsWith(event.getType(), OperationDataType.UPDATE.toString()));

        submitData = CastUtils.cast(event.getData().get(OperationDataTraceRecord.SUBMIT_DATA_FIELD));
        Assertions.assertEquals(objectMapper.writeValueAsString(submitData.get("status")), objectMapper.writeValueAsString(DisabledOrEnabled.Disabled));

        allTypeEntityService.deleteByEntity(entity);
        events = auditEventRepository.find(null, null, null);
        Assertions.assertEquals(4, events.size());

        event = events.getLast();
        Assertions.assertEquals(event.getPrincipal(), InetAddress.getLocalHost().getHostAddress());
        Assertions.assertTrue(Strings.CS.endsWith(event.getType(), OperationDataType.DELETE.toString()));

        entity.setId(null);
        allTypeEntityService.save(entity);
        events = auditEventRepository.find(null, null, null);
        Assertions.assertEquals(5, events.size());

        event = events.getLast();
        submitData = CastUtils.cast(event.getData().get(OperationDataTraceRecord.SUBMIT_DATA_FIELD));

        Assertions.assertEquals(event.getPrincipal(), InetAddress.getLocalHost().getHostAddress());
        Assertions.assertEquals(submitData.get(IdEntity.ID_FIELD_NAME), entity.getId());
        Assertions.assertTrue(Strings.CS.endsWith(event.getType(), OperationDataType.INSERT.toString()));

        allTypeEntityService.lambdaUpdate().eq(AllTypeEntity::getId, entity.getId()).remove();
        events = auditEventRepository.find(null, null, null);
        Assertions.assertEquals(6, events.size());

        event = events.getLast();
        Assertions.assertEquals(event.getPrincipal(), InetAddress.getLocalHost().getHostAddress());
        Assertions.assertTrue(Strings.CS.endsWith(event.getType(), OperationDataType.DELETE.toString()));

    }

}
