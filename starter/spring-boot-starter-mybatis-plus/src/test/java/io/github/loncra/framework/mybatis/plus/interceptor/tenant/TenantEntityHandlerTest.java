package io.github.loncra.framework.mybatis.plus.interceptor.tenant;

import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;
import io.github.loncra.framework.mybatis.plus.tenant.TenantLinePolicy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * {@link TenantEntityHandler} 单元测试（不依赖 Spring 容器）。
 */
class TenantEntityHandlerTest {

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void getTenantIdIsNullWithoutTenantContext() {
        TenantEntityHandler handler = new TenantEntityHandler(Set.of("tb_demo"), (tenantContext) -> false);
        TenantContextHolder.clear();
        Assertions.assertNull(handler.getTenantId());
    }


}
