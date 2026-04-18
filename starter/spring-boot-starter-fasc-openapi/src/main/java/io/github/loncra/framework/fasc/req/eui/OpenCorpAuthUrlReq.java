package io.github.loncra.framework.fasc.req.eui;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @ClassName: OpenCorpAuthenticationUrlReq
 * @Description:
 * @Author: hukc@fadada.com
 * @Date: 2022/3/24 15:29
 */
public class OpenCorpAuthUrlReq extends BaseReq {

    private String clientCorpId;
    private String corpName;
    private String redirectUrl;
    private String authScopes;


    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAuthScopes() {
        return authScopes;
    }

    public void setAuthScopes(String authScopes) {
        this.authScopes = authScopes;
    }

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
    }
}
