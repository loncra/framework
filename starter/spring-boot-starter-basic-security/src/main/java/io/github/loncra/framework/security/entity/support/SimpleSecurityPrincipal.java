package io.github.loncra.framework.security.entity.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.enumerate.security.UserStatus;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * 简单的安全用户实现
 *
 * @author maurice.chen
 */
public class SimpleSecurityPrincipal implements SecurityPrincipal {

    /**
     * 用户 id
     */
    private Object id;

    /**
     * 凭证信息
     */
    @JsonIgnore
    private Object credentials;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户状态
     */
    @JsonIgnore
    private UserStatus status;

    /**
     * 构造函数
     */
    public SimpleSecurityPrincipal() {
    }

    /**
     * 构造函数
     *
     * @param splitString 分割字符串，格式为：用户id:用户名
     * @param credentials 凭证信息
     * @param status      用户状态
     */
    public SimpleSecurityPrincipal(
            String splitString,
            Object credentials,
            UserStatus status
    ) {
        String[] split = StringUtils.splitByWholeSeparator(splitString, CacheProperties.DEFAULT_SEPARATOR);
        Assert.isTrue(split.length == 2, "分割字符串错误，格式应该为: <用户 id>:<用户登录信息>");
        this.id = split[0];
        this.username = split[1];
        this.credentials = credentials;
        this.status = status;
    }

    /**
     * 构造函数（默认状态为启用）
     *
     * @param id          用户 id
     * @param credentials 凭证信息
     * @param username    用户名
     */
    public SimpleSecurityPrincipal(
            Object id,
            Object credentials,
            String username
    ) {
        this(id, credentials, username, UserStatus.Enabled);
    }

    /**
     * 构造函数
     *
     * @param id          用户 id
     * @param credentials 凭证信息
     * @param username    用户名
     * @param status      用户状态
     */
    public SimpleSecurityPrincipal(
            Object id,
            Object credentials,
            String username,
            UserStatus status
    ) {
        this.id = id;
        this.credentials = credentials;
        this.username = username;
        this.status = status;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    /**
     * 设置凭证信息
     *
     * @param credentials 凭证信息
     */
    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户状态
     *
     * @return 用户状态
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * 设置用户状态
     *
     * @param status 用户状态
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    @JsonIgnore
    public boolean isNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isNonLocked() {
        return !UserStatus.Lock.equals(status);
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isDisabled() {
        return UserStatus.Disabled.equals(status);
    }
}
