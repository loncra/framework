package io.github.loncra.framework.fasc.req.template;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author Fadada
 * @date 2021/8/5 11:07:53
 */
public class GetTemplateManageUrlReq extends BaseReq {

    private String openCorpId;
    private String redirectUrl;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
