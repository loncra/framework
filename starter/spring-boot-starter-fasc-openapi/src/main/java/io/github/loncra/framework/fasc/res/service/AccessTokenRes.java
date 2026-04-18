package io.github.loncra.framework.fasc.res.service;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/10 18:10:03
 */
public class AccessTokenRes extends BaseBean {

    private String accessToken;

    /**
     * 有效期截至时间(毫秒)
     */
    private String expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
