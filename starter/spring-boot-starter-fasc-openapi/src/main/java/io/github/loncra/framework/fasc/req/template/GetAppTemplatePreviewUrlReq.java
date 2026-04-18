package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:45
 */
public class GetAppTemplatePreviewUrlReq extends BaseReq {
    private String appTemplateId;
    private String redirectUrl;

    public String getAppTemplateId() {
        return appTemplateId;
    }

    public void setAppTemplateId(String appTemplateId) {
        this.appTemplateId = appTemplateId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
