package io.github.loncra.framework.fasc.res.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * @date 2021/12/15 14:04:46
 */
public class EUrlRes extends BaseBean {
    private String authUrl;
    private String eUrl;
    private String authShortUrl;

    public String geteUrl() {
        return eUrl;
    }

    public void seteUrl(String eUrl) {
        this.eUrl = eUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAuthShortUrl() {
        return authShortUrl;
    }

    public void setAuthShortUrl(String authShortUrl) {
        this.authShortUrl = authShortUrl;
    }
}
