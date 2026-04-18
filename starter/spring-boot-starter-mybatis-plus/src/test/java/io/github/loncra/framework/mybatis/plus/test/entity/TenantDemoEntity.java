package io.github.loncra.framework.mybatis.plus.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.loncra.framework.commons.tenant.TenantEntity;
import io.github.loncra.framework.mybatis.plus.interceptor.tenant.TenantEntityHandler;

import java.io.Serial;

/**
 * 用于验证 {@link TenantEntityHandler} 与租户行插件的测试实体。
 */
@TableName("tb_tenant_demo")
public class TenantDemoEntity implements TenantEntity<String> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private String tenantId;

    private String name;

    public TenantDemoEntity() {
    }

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
