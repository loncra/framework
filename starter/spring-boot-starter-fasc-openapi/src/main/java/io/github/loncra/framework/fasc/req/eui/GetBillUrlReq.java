package io.github.loncra.framework.fasc.req.eui;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author Fadada
 * @date 2021/12/15 15:03:06
 */
public class GetBillUrlReq extends BaseReq {
    private OpenId openId;
    private String urlType;
    private String redirectUrl;
    private String clientUserId;

    public OpenId getOpenId() {
        return openId;
    }

    public void setOpenId(OpenId openId) {
        this.openId = openId;
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

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }
}
