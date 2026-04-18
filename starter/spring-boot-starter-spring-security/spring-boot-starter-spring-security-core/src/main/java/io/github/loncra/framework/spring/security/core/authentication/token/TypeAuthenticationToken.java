package io.github.loncra.framework.spring.security.core.authentication.token;

import io.github.loncra.framework.commons.CacheProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.SpringSecurityMessageSource;

import java.io.Serial;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * 带类型的认证 token，用于区分那种类型的用户使用
 *
 * @author maurice.chen
 */
public class TypeAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = -6826606139646961317L;
    private final Object principal;

    private final Object credentials;

    private final String type;

    public TypeAuthenticationToken(
            Object principal,
            Object credentials,
            String type
    ) {
        super(new LinkedHashSet<>());
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return getType() + CacheProperties.DEFAULT_SEPARATOR + getPrincipal();
    }

    public static TypeAuthenticationToken ofString(
            String splitString,
            Object credentials,
            Object details
    ) {
        String[] split = StringUtils.splitByWholeSeparator(splitString, CacheProperties.DEFAULT_SEPARATOR);


        if (ArrayUtils.isEmpty(split) || split.length < 2) {
            String message = SpringSecurityMessageSource.getAccessor().getMessage(
                    "TypeAuthenticationToken.formatError",
                    "登录数据出错，格式应该为:<用户类型>:<用户id>:[用户登录信息]， 当前格式为:" + splitString
            );
            throw new InternalAuthenticationServiceException(message);
        }

        TypeAuthenticationToken token = new TypeAuthenticationToken(
                split.length == 3 ? split[2] : split[1],
                credentials,
                split[0]
        );

        if (Objects.nonNull(details)) {
            token.setDetails(details);
        }

        return token;
    }
}
