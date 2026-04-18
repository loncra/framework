package io.github.loncra.framework.commons.domain;

import io.github.loncra.framework.commons.TimeProperties;

import java.io.Serial;

/**
 * 刷新 token
 *
 * @author maurice.chen
 */
public class RefreshToken extends AccessToken {

    @Serial
    private static final long serialVersionUID = 8033095951815144693L;
    /**
     * 对应的访问 token
     */
    private AccessToken accessToken;

    public RefreshToken() {
    }

    public RefreshToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public RefreshToken(
            String token,
            TimeProperties expiresTime,
            AccessToken accessToken
    ) {
        super(token, expiresTime);
        this.accessToken = accessToken;
    }

    public RefreshToken(
            AccessToken refreshToken,
            AccessToken accessToken
    ) {
        this(refreshToken.getValue(), refreshToken.getExpiresTime(), accessToken);
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
