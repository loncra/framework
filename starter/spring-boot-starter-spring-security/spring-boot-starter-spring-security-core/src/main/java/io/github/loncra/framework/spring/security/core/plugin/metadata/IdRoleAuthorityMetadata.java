package io.github.loncra.framework.spring.security.core.plugin.metadata;

import io.github.loncra.framework.commons.enumerate.basic.DisabledOrEnabled;
import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.security.entity.RoleAuthority;

import java.io.Serial;


/**
 * 带 id 的 RoleAuthority 实现，用于用户被分配组时存储 json 格式使用
 *
 * @author maurice.chen
 */
public class IdRoleAuthorityMetadata extends RoleAuthority implements BasicIdentification<Long> {

    @Serial
    private static final long serialVersionUID = 5630516721501929606L;

    /**
     * 主键 id
     */
    private Long id;

    /**
     * 是否启用
     */
    private DisabledOrEnabled status = DisabledOrEnabled.Enabled;

    public IdRoleAuthorityMetadata() {
    }

    public IdRoleAuthorityMetadata(RoleAuthority roleAuthority) {
        super(roleAuthority.getName(), roleAuthority.getAuthority());
    }

    public IdRoleAuthorityMetadata(
            Long id,
            String name,
            String authority
    ) {
        super(name, authority);
        this.id = id;
    }

    public IdRoleAuthorityMetadata(
            Long id,
            String name,
            String authority,
            DisabledOrEnabled status
    ) {
        this(id, name, authority);
        this.status = status;
    }

    public static IdRoleAuthorityMetadata of(
            Long id,
            String name,
            String authority
    ) {
        return new IdRoleAuthorityMetadata(id, name, authority);
    }

    public static IdRoleAuthorityMetadata of(
            Long id,
            String name,
            String authority,
            DisabledOrEnabled status
    ) {
        return new IdRoleAuthorityMetadata(id, name, authority, status);
    }

    @Override
    public String getAuthority() {
        return super.getAuthority();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public DisabledOrEnabled getStatus() {
        return status;
    }

    public void setStatus(DisabledOrEnabled status) {
        this.status = status;
    }
}
