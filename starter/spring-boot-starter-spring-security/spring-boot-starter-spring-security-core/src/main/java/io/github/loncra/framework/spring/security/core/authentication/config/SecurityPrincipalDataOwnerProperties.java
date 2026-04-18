package io.github.loncra.framework.spring.security.core.authentication.config;

import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

/**
 * 安全用户数据拥有者配置
 *
 * @author maurice.chen
 */
@ConfigurationProperties("loncra.framework.authentication.data-owner")
public class SecurityPrincipalDataOwnerProperties {

    /**
     * 要忽略的当前用户类型，配置后如果 {@link AuditAuthenticationToken#getName()} 包含该值信息就不做数据权限控制
     */
    private List<String> ignorePrincipalTypes = new LinkedList<>();

    /**
     * 要忽略的当前用户名称，配置后如果 {@link AuditAuthenticationToken#getPrincipal#toString() }
     * 或者  {@link AuditAuthenticationToken#getPrincipal} 实现 {@link Principal} 并且 {@link Principal#getName()}
     * 值包含该值信息就不做数据权限控制
     */
    private List<String> ignorePrincipalNames = new LinkedList<>();

    public SecurityPrincipalDataOwnerProperties() {
    }

    public List<String> getIgnorePrincipalTypes() {
        return ignorePrincipalTypes;
    }

    public void setIgnorePrincipalTypes(List<String> ignorePrincipalTypes) {
        this.ignorePrincipalTypes = ignorePrincipalTypes;
    }

    public List<String> getIgnorePrincipalNames() {
        return ignorePrincipalNames;
    }

    public void setIgnorePrincipalNames(List<String> ignorePrincipalNames) {
        this.ignorePrincipalNames = ignorePrincipalNames;
    }
}
