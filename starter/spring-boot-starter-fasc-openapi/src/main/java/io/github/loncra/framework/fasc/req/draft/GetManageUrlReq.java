package io.github.loncra.framework.fasc.req.draft;

import io.github.loncra.framework.fasc.bean.base.BaseReq;


public class GetManageUrlReq extends BaseReq {

    private String openCorpId;

    private String clientUserId;

    private String redirectUrl;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
