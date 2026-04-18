package io.github.loncra.framework.fasc.req.archives;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetArchivesManageUrlReq extends BaseReq {

    /**
     * 法大大平台为该企业在该应用appId范围内分配的唯一标识
     */
    private String openCorpId;

    /**
     * 应用系统中唯一确定登录用户身份的标识
     */
    private String clientUserId;

    /**
     * 重定向地址
     */
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
