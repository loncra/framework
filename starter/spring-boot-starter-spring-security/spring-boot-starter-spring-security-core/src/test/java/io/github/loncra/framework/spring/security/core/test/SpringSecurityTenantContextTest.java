package io.github.loncra.framework.spring.security.core.test;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.loncra.framework.commons.tenant.SimpleTenantContext;
import io.github.loncra.framework.commons.tenant.TenantEntity;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;
import io.github.loncra.framework.mybatis.plus.interceptor.tenant.TenantEntityHandler;
import io.github.loncra.framework.spring.security.core.authentication.TenantContextSecurityFilter;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.test.dao.SecurityTenantResourceDao;
import io.github.loncra.framework.spring.security.core.test.entity.SecurityTenantResourceEntity;
import io.github.loncra.framework.spring.security.core.test.service.SecurityTenantResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 端到端验证 Spring Security 与 MyBatis-Plus 租户栈一致：
 * <ol>
 *   <li>{@link TenantContextSecurityFilter}
 *   根据认证元数据写入 {@link TenantContextHolder}</li>
 *   <li>Controller → {@link SecurityTenantResourceService}
 *   → {@link SecurityTenantResourceDao} 走
 *   {@link TenantEntityHandler}，
 *   对实现 {@link TenantEntity} 的实体在 CRUD 中附加 {@code tenant_id}</li>
 *   <li>断言阶段在测试线程显式设置与登录用户一致的 {@link TenantContextHolder}，仅用 MyBatis-Plus 查询校验物理行与租户隔离（不使用 JDBC）</li>
 * </ol>
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringSecurityTenantContextTest {

    private static final String TENANT_TEST = "tenant-user-test";
    private static final String TENANT_ADMIN = "tenant-user-admin";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Autowired
    private SecurityTenantResourceDao securityTenantResourceDao;

    @Test
    void crudThroughControllerUsesMybatisPlusTenantLineAndPersistsTenantIdColumn() throws Exception {
        MockHttpSession session = new MockHttpSession();
        login(session, "test", "123456");

        mockMvc.perform(get("/tenant-db/tenant-id").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(TENANT_TEST));

        mockMvc.perform(post("/tenant-db/resources")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"row-a\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("row-a"))
                .andExpect(jsonPath("$.data.tenantId").value(TENANT_TEST));

        assertEquals(TENANT_TEST, mpSelectById(1).getTenantId());
        assertEquals("row-a", mpSelectById(1).getName());

        mockMvc.perform(get("/tenant-db/resources").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].tenantId").value(TENANT_TEST));

        mockMvc.perform(put("/tenant-db/resources/1")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"row-a2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("row-a2"));

        assertEquals("row-a2", mpSelectById(1).getName());
        assertEquals(TENANT_TEST, mpSelectById(1).getTenantId());

        mockMvc.perform(delete("/tenant-db/resources/1").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));

        assertNull(mpSelectById(1));
        assertEquals(0L, mpCount(TENANT_TEST));

        mockMvc.perform(get("/tenant-db/tenant-id").session(session))
                .andExpect(jsonPath("$.data").value(TENANT_TEST));
    }

    @Test
    void twoTenantsIsolatedByTenantLineAndCrossTenantMutationsSeeNothing() throws Exception {
        MockHttpSession sessionTest = new MockHttpSession();
        login(sessionTest, "test", "123456");
        mockMvc.perform(post("/tenant-db/resources")
                        .session(sessionTest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"only-test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));

        MockHttpSession sessionAdmin = new MockHttpSession();
        login(sessionAdmin, "admin", "123456");
        mockMvc.perform(get("/tenant-db/resources").session(sessionAdmin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0));

        mockMvc.perform(post("/tenant-db/resources")
                        .session(sessionAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"only-admin\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.tenantId").value(TENANT_ADMIN));

        assertEquals(1L, mpCount(TENANT_TEST));
        assertEquals(1L, mpCount(TENANT_ADMIN));
        assertEquals(TENANT_TEST, mpSelectUnderTenant(TENANT_TEST, 1).getTenantId());
        assertEquals(TENANT_ADMIN, mpSelectUnderTenant(TENANT_ADMIN, 2).getTenantId());

        mockMvc.perform(get("/tenant-db/resources").session(sessionTest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value("only-test"));

        assertNull(mpSelectUnderTenant(TENANT_ADMIN, 1));
        assertNotNull(mpSelectUnderTenant(TENANT_TEST, 1));

        mockMvc.perform(put("/tenant-db/resources/1")
                        .session(sessionAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"hacked\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404));

        assertEquals("only-test", mpSelectUnderTenant(TENANT_TEST, 1).getName());

        mockMvc.perform(delete("/tenant-db/resources/1").session(sessionAdmin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(404));

        assertNotNull(mpSelectUnderTenant(TENANT_TEST, 1));
    }

    @Test
    void unauthenticatedRequestGetsUnauthorized() throws Exception {
        mockMvc.perform(get("/tenant-db/tenant-id"))
                .andExpect(status().isUnauthorized());
    }

    private void login(MockHttpSession session, String username, String password) throws Exception {
        mockMvc.perform(
                        post(authenticationProperties.getLoginProcessingUrl())
                                .param(authenticationProperties.getUsernameParamName(), username)
                                .param(authenticationProperties.getPasswordParamName(), password)
                                .header(authenticationProperties.getTypeHeaderName(), "test")
                                .session(session)
                )
                .andExpect(status().isOk());
    }

    /**
     * 在指定租户上下文中使用 MyBatis-Plus 查询
     */
    private SecurityTenantResourceEntity mpSelectUnderTenant(String tenantId, Integer id) {
        return withTenant(tenantId, () -> securityTenantResourceDao.selectById(id));
    }

    private SecurityTenantResourceEntity mpSelectById(Integer id) {
        return withTenant(TENANT_TEST, () -> securityTenantResourceDao.selectById(id));
    }

    private long mpCount(String tenantId) {
        return withTenant(
                tenantId,
                () -> securityTenantResourceDao.selectCount(Wrappers.<SecurityTenantResourceEntity>lambdaQuery())
        );
    }

    private static <T> T withTenant(String tenantId, Supplier<T> supplier) {
        TenantContextHolder.set(new SimpleTenantContext(tenantId));
        try {
            return supplier.get();
        }
        finally {
            TenantContextHolder.clear();
        }
    }
}
