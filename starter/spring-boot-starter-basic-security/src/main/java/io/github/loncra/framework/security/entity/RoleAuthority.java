package io.github.loncra.framework.security.entity;


import io.github.loncra.framework.commons.enumerate.NameEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * spring security 角色授权现实
 *
 * @author maurice.chen
 */
public class RoleAuthority implements Serializable {

    @Serial
    private static final long serialVersionUID = 405960799851529326L;

    /**
     * 默认角色前缀
     */
    public static final String DEFAULT_ROLE_PREFIX = "ROLE_";

    /**
     * 默认权限字段名称
     */
    public static final String DEFAULT_AUTHORITY_FIELD_NAME = "authority";

    /**
     * 名称
     */
    private String name;

    /**
     * spring security role 的 authority name 值
     */
    private String authority;

    /**
     * spring security 角色授权现实
     *
     * @param authority spring security role 的 authority name 值
     */
    public RoleAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * spring security 角色授权现实
     *
     * @param name      名称
     * @param authority spring security role 的 authority name 值
     */
    public RoleAuthority(
            String name,
            String authority
    ) {
        this(authority);
        this.name = name;
    }

    /**
     * 构造函数
     *
     * @param map 包含 name 和 authority 字段的 Map
     */
    public RoleAuthority(Map<String, Object> map) {
        this(map.get(NameEnum.FIELD_NAME).toString(), map.get(DEFAULT_AUTHORITY_FIELD_NAME).toString());
    }

    /**
     * spring security 角色授权现实
     */
    /**
     * 构造函数
     */
    public RoleAuthority() {
    }

    /**
     * 获取 spring security role 的 authority name 值
     *
     * @return spring security role 的 authority name 值
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 spring security role 的 authority name 值
     *
     * @param authority spring security role 的 authority name 值
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
