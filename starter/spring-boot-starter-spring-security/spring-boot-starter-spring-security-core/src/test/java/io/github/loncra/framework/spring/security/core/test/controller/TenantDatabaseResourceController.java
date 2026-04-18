package io.github.loncra.framework.spring.security.core.test.controller;

import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.commons.tenant.holder.TenantContextHolder;
import io.github.loncra.framework.spring.security.core.test.entity.SecurityTenantResourceEntity;
import io.github.loncra.framework.spring.security.core.test.service.SecurityTenantResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通过 MyBatis-Plus 租户行插件访问 {@code tb_security_tenant_resource}，验证 {@link TenantContextHolder} 与数据库租户列一致。
 */
@RestController
@RequestMapping("tenant-db")
@PreAuthorize("isAuthenticated()")
public class TenantDatabaseResourceController {

    private final SecurityTenantResourceService securityTenantResourceService;

    public TenantDatabaseResourceController(SecurityTenantResourceService securityTenantResourceService) {
        this.securityTenantResourceService = securityTenantResourceService;
    }

    @GetMapping("tenant-id")
    public RestResult<Object> currentTenantIdFromContext() {
        return RestResult.ofSuccess(TenantContextHolder.get().getId());
    }

    @PostMapping("resources")
    public RestResult<SecurityTenantResourceEntity> create(@RequestBody NamePayload payload) {
        SecurityTenantResourceEntity entity = new SecurityTenantResourceEntity();
        entity.setName(payload.name());
        securityTenantResourceService.save(entity);
        // 租户列由 TenantLineInnerInterceptor 写入 SQL，内存实体未回填，需再查库得到 tenant_id
        return RestResult.ofSuccess(securityTenantResourceService.get(entity.getId()));
    }

    @GetMapping("resources")
    public RestResult<List<SecurityTenantResourceEntity>> list() {
        return RestResult.ofSuccess(securityTenantResourceService.find());
    }

    @PutMapping("resources/{id}")
    public RestResult<SecurityTenantResourceEntity> update(@PathVariable Integer id, @RequestBody NamePayload payload) {
        SecurityTenantResourceEntity existing = securityTenantResourceService.get(id);
        if (existing == null) {
            return RestResult.of("not found", HttpStatus.NOT_FOUND.value(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        existing.setName(payload.name());
        securityTenantResourceService.updateById(existing);
        return RestResult.ofSuccess(securityTenantResourceService.get(id));
    }

    @DeleteMapping("resources/{id}")
    public RestResult<Boolean> delete(@PathVariable Integer id) {
        int removed = securityTenantResourceService.deleteById(id);
        if (removed <= 0) {
            return RestResult.of("not found", HttpStatus.NOT_FOUND.value(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        }
        return RestResult.ofSuccess(Boolean.TRUE);
    }

    public record NamePayload(String name) {
    }
}
