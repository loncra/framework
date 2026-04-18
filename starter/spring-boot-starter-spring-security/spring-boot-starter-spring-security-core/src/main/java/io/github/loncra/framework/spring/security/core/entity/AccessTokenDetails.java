package io.github.loncra.framework.spring.security.core.entity;

import io.github.loncra.framework.commons.domain.ExpiredToken;

import java.io.Serializable;

/**
 * 访问令牌认证明细, 用于自动生成访问令牌使用
 *
 * @author maurice.chen
 */
public interface AccessTokenDetails extends Serializable {

    /**
     * 获取访问令牌
     *
     * @return 访问令牌
     */
    ExpiredToken getToken();

    /**
     * 设置访问令牌
     *
     * @param token 访问令牌
     */
    void setToken(ExpiredToken token);
}
