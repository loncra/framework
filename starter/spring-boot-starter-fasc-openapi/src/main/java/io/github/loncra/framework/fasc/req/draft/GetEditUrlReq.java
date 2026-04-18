package io.github.loncra.framework.fasc.req.draft;

import io.github.loncra.framework.fasc.bean.base.BaseReq;


public class GetEditUrlReq extends BaseReq {

    private String openCorpId;

    private String clientUserId;

    private String contractConsultId;

    private String urlType;

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

    public String getContractConsultId() {
        return contractConsultId;
    }

    public void setContractConsultId(String contractConsultId) {
        this.contractConsultId = contractConsultId;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
