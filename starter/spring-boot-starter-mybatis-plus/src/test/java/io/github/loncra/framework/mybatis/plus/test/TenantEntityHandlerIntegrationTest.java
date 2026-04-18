package io.github.loncra.framework.mybatis.plus.test;

import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;
import io.github.loncra.framework.mybatis.plus.test.entity.TenantDemoEntity;
import io.github.loncra.framework.mybatis.plus.test.mapper.AllTypeEntityMapper;
import io.github.loncra.framework.mybatis.plus.test.mapper.TenantDemoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 集成测试：{@code MybatisPlusInterceptor} 中的 {@code TenantLineInnerInterceptor} + {@code TenantEntityHandler}
 * 仅对「带 @Mapper 且实体实现 TenantEntity 并声明 @TableName」的表追加租户条件。
 */
@SpringBootTest
@Transactional
public class TenantEntityHandlerIntegrationTest {

    @Autowired
    private TenantDemoMapper tenantDemoMapper;

    @Autowired
    private AllTypeEntityMapper allTypeEntityMapper;

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }


    @Test
    void queryOnlySeesCurrentTenantRows() {
        TenantContextHolder.set(new SimpleTenantContext("tenant-a"));
        TenantDemoEntity rowA = new TenantDemoEntity();
        rowA.setName("row-a");
        tenantDemoMapper.insert(rowA);

        TenantContextHolder.set(new SimpleTenantContext(("tenant-b")));
        TenantDemoEntity rowB = new TenantDemoEntity();
        rowB.setName("row-b");
        tenantDemoMapper.insert(rowB);

        List<TenantDemoEntity> listB = tenantDemoMapper.selectList(null);
        Assertions.assertEquals(1, listB.size());
        Assertions.assertEquals("row-b", listB.getFirst().getName());
        Assertions.assertEquals("tenant-b", listB.getFirst().getTenantId());

        TenantContextHolder.set(new SimpleTenantContext(("tenant-a")));
        List<TenantDemoEntity> listA = tenantDemoMapper.selectList(null);
        Assertions.assertEquals(1, listA.size());
        Assertions.assertEquals("row-a", listA.getFirst().getName());
        Assertions.assertEquals("tenant-a", listA.getFirst().getTenantId());
    }

    @Test
    void selectCountWorksWithoutTenantContext() {
        TenantContextHolder.clear();
        Long count = allTypeEntityMapper.selectCount(null);
        Assertions.assertEquals(10L, count);
    }

    @Test
    void insertPersistsTenantIdFromContext() {
        TenantContextHolder.set(new SimpleTenantContext("tenant-insert"));
        TenantDemoEntity row = new TenantDemoEntity();
        row.setName("inserted");
        Assertions.assertNull(row.getTenantId());
        tenantDemoMapper.insert(row);
        Assertions.assertNotNull(row.getId());

        TenantDemoEntity loaded = tenantDemoMapper.selectById(row.getId());
        Assertions.assertNotNull(loaded);
        Assertions.assertEquals("tenant-insert", loaded.getTenantId());
        Assertions.assertEquals("inserted", loaded.getName());
    }

    @Test
    void updateByIdOnlyAffectsCurrentTenantRows() {
        TenantContextHolder.set(new SimpleTenantContext("tenant-u1"));
        TenantDemoEntity row1 = new TenantDemoEntity();
        row1.setName("u1");
        tenantDemoMapper.insert(row1);
        Integer id1 = row1.getId();

        TenantContextHolder.set(new SimpleTenantContext("tenant-u2"));
        TenantDemoEntity row2 = new TenantDemoEntity();
        row2.setName("u2");
        tenantDemoMapper.insert(row2);
        Integer id2 = row2.getId();

        TenantContextHolder.set(new SimpleTenantContext("tenant-u1"));
        TenantDemoEntity attack = new TenantDemoEntity();
        attack.setId(id2);
        attack.setName("hacked");
        Assertions.assertEquals(0, tenantDemoMapper.updateById(attack));
        TenantContextHolder.set(new SimpleTenantContext("tenant-u2"));
        Assertions.assertEquals("u2", tenantDemoMapper.selectById(id2).getName());

        TenantContextHolder.set(new SimpleTenantContext("tenant-u1"));
        TenantDemoEntity legit = new TenantDemoEntity();
        legit.setId(id1);
        legit.setName("u1-renamed");
        Assertions.assertEquals(1, tenantDemoMapper.updateById(legit));
        Assertions.assertEquals("u1-renamed", tenantDemoMapper.selectById(id1).getName());
    }


    @Test
    void deleteByIdOnlyAffectsCurrentTenantRows() {
        TenantContextHolder.set(new SimpleTenantContext("tenant-d1"));
        TenantDemoEntity row1 = new TenantDemoEntity();
        row1.setName("d1");
        tenantDemoMapper.insert(row1);
        Integer id1 = row1.getId();

        TenantContextHolder.set(new SimpleTenantContext("tenant-d2"));
        TenantDemoEntity row2 = new TenantDemoEntity();
        row2.setName("d2");
        tenantDemoMapper.insert(row2);
        Integer id2 = row2.getId();

        TenantContextHolder.set(new SimpleTenantContext("tenant-d1"));
        Assertions.assertEquals(0, tenantDemoMapper.deleteById(id2));
        TenantContextHolder.set(new SimpleTenantContext("tenant-d2"));
        Assertions.assertNotNull(tenantDemoMapper.selectById(id2));

        TenantContextHolder.set(new SimpleTenantContext("tenant-d1"));
        Assertions.assertEquals(1, tenantDemoMapper.deleteById(id1));
        Assertions.assertNull(tenantDemoMapper.selectById(id1));
    }

    private static Throwable rootCause(Throwable t) {
        Throwable c = t;
        while (c.getCause() != null && c.getCause() != c) {
            c = c.getCause();
        }
        return c;
    }
}
