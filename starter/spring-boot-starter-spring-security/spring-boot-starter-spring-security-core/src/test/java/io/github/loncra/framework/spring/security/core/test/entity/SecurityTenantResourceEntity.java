package io.github.loncra.framework.spring.security.core.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.loncra.framework.commons.tenant.TenantEntity;
import io.github.loncra.framework.mybatis.plus.interceptor.tenant.TenantEntityHandler;

import java.io.Serial;

/**
 * 租户隔离表实体，供 Spring Security 与 {@link TenantEntityHandler} 集成测试使用。
 */
@TableName("tb_security_tenant_resource")
public class SecurityTenantResourceEntity implements TenantEntity<String> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tenantId;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
