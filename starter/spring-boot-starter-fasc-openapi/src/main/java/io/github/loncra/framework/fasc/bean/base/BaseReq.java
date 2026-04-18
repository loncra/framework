package io.github.loncra.framework.fasc.bean.base;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public class BaseReq extends BaseBean {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
