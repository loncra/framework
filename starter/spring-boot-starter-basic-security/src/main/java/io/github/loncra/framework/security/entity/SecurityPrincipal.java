package io.github.loncra.framework.security.entity;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.id.BasicIdentification;
import org.apache.commons.lang3.StringUtils;

import java.security.Principal;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 安全用户接口，拥有描述一个完整登录后的用户。
 *
 * @author maurice.chen
 */
public interface SecurityPrincipal extends Principal, BasicIdentification<Object> {

    /**
     * 获取账户唯一识别
     *
     * @return 唯一识别
     */
    @Override
    Object getId();

    /**
     * 获取凭证
     *
     * @return 凭证
     */
    Object getCredentials();

    /**
     * 获取登录帐号
     *
     * @return 登录帐号
     */
    String getUsername();

    /**
     * 判断账号是否过期
     *
     * @return true 是，否则 false
     */
    boolean isNonExpired();

    /**
     * 判断账号是否未锁定
     *
     * @return true 是，否则 false
     */
    boolean isNonLocked();

    /**
     * 判断密码是否过期
     *
     * @return true 是，否则 false
     */
    boolean isCredentialsNonExpired();

    /**
     * 判断账户是否禁用
     *
     * @return true 是，否则 false
     */
    boolean isDisabled();

    @Override
    default String getName() {
        return Stream.of(getId(), getUsername())
                .filter(Objects::nonNull)
                .map(Object::toString)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(CacheProperties.DEFAULT_SEPARATOR));
    }

}
