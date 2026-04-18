package io.github.loncra.framework.spring.security.core.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.loncra.framework.mybatis.plus.baisc.support.IntegerVersionEntity;
import io.github.loncra.framework.security.audit.AuditPrincipal;

import java.io.Serial;

@TableName(value = "tb_operation_data", autoResultMap = true)
public class OperationDataEntity extends IntegerVersionEntity<Integer> implements AuditPrincipal {

    @Serial
    private static final long serialVersionUID = 6475237876826796114L;

    private String name;

    private String owner;

    private String principal;

    public OperationDataEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }

    @Override
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
