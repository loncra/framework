package io.github.loncra.framework.spring.security.core.authentication.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.enumerate.security.UserStatus;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.security.entity.support.SimpleSecurityPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;

import java.io.Serial;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 简单的用户认证审计 token
 *
 * @author maurice.chen
 */
public class AuditAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 3747271533448473641L;

    /**
     * 详情键名
     */
    public static final String DETAILS_KEY = "details";

    /**
     * 授权键名
     */
    public static final String AUTHORITIES_KEY = "authorities";

    /**
     * 当前用户
     */
    private final SecurityPrincipal principal;

    /**
     * 最后认证时间
     */
    private final Instant lastAuthenticationTime;

    /**
     * 当前用户类型
     */
    private final String type;

    /**
     * 是否记住我认证
     */
    private boolean isRememberMe = false;

    /**
     * 构造函数
     *
     * @param principal              当前用户
     * @param type                   用户类型
     * @param authorities            授权信息
     * @param lastAuthenticationTime 最后认证时间
     */
    public AuditAuthenticationToken(
            SecurityPrincipal principal,
            String type,
            Collection<? extends GrantedAuthority> authorities,
            Instant lastAuthenticationTime
    ) {
        super(authorities);
        this.principal = principal;
        this.type = type;
        this.lastAuthenticationTime = lastAuthenticationTime;
    }

    /**
     * 构造函数
     *
     * @param principal 当前用户
     * @param token     类型认证令牌
     */
    public AuditAuthenticationToken(
            SecurityPrincipal principal,
            TypeAuthenticationToken token
    ) {
        this(principal, token, new LinkedHashSet<>());
    }

    /**
     * 构造函数
     *
     * @param principal          当前用户
     * @param token              类型认证令牌
     * @param grantedAuthorities 授予的权限集合
     */
    public AuditAuthenticationToken(
            SecurityPrincipal principal,
            TypeAuthenticationToken token,
            Collection<? extends GrantedAuthority> grantedAuthorities
    ) {
        this(principal, token.getType(), grantedAuthorities, Instant.now());
    }

    @Override
    public Object getCredentials() {
        return principal.getCredentials();
    }

    @Override
    public Object getPrincipal() {
        return getSecurityPrincipal();
    }

    @JsonIgnore
    public SecurityPrincipal getSecurityPrincipal() {
        return principal;
    }

    @Override
    public String getName() {
        return getType() + CacheProperties.DEFAULT_SEPARATOR + principal.getId();
    }

    /**
     * 获取完整名称
     *
     * @return 完整名称
     */
    public String getFullName() {
        return getType() + CacheProperties.DEFAULT_SEPARATOR + principal.getName();
    }

    /**
     * 获取用户类型
     *
     * @return 用户类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取最后认证时间
     *
     * @return 最后认证时间
     */
    public Instant getLastAuthenticationTime() {
        return lastAuthenticationTime;
    }

    /**
     * 是否记住我认证
     *
     * @return true 是，否则 false
     */
    public boolean isRememberMe() {
        return isRememberMe;
    }

    /**
     * 设置是否记住我认证
     *
     * @param rememberMe true 是，否则 false
     */
    public void setRememberMe(boolean rememberMe) {
        isRememberMe = rememberMe;
    }

    /**
     * 获取字符串数组形式的授权信息集合
     *
     * @return 字符串数组形式的授权信息集合
     */
    public Collection<String> getGrantedAuthorities() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    /**
     * 从字符串创建审计认证令牌
     *
     * @param splitString        分割字符串（格式：用户类型:用户ID:用户名）
     * @param grantedAuthorities 授予的权限集合
     *
     * @return 审计认证令牌
     */
    public static AuditAuthenticationToken ofString(
            String splitString,
            Collection<? extends GrantedAuthority> grantedAuthorities
    ) {
        String type = StringUtils.substringBefore(splitString, CacheProperties.DEFAULT_SEPARATOR);
        if (StringUtils.isEmpty(type)) {
            String message = SpringSecurityMessageSource.getAccessor().getMessage(
                    "AuditAuthenticationToken.formatError",
                    "登录数据出错，格式应该为:<用户类型>:<用户ID>:<用户登录信息>， 当前格式为:" + splitString
            );
            throw new InternalAuthenticationServiceException(message);
        }

        String principalString = StringUtils.substringAfter(splitString, CacheProperties.DEFAULT_SEPARATOR);
        SimpleSecurityPrincipal principal = new SimpleSecurityPrincipal(principalString, null, UserStatus.Disabled);

        return new AuditAuthenticationToken(principal, type, grantedAuthorities, Instant.now());
    }
}
