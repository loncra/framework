package io.github.loncra.framework.spring.security.core.plugin.metadata;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.id.BasicIdentification;
import io.github.loncra.framework.security.entity.ResourceAuthority;

import java.io.Serial;
import java.util.Objects;

/**
 * 带 id 的资源认证元数据信息
 *
 * @author maurice.chen
 */
public class IdResourceAuthorityMetadata<T> extends ResourceAuthority implements BasicIdentification<T> {

    @Serial
    private static final long serialVersionUID = -8157345214033745221L;

    private T id;

    private String applicationName;

    public IdResourceAuthorityMetadata() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdResourceAuthorityMetadata<?> that = CastUtils.cast(o);
        return Objects.equals(id, that.id) && Objects.equals(applicationName, that.applicationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationName, getAuthority());
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public void setId(T id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
