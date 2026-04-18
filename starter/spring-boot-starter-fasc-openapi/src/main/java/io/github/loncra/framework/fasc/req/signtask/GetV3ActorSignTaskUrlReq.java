package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

public class GetV3ActorSignTaskUrlReq extends BaseReq {

    private OpenId ownerId;
    private String signTaskId;
    private String redirectUrl;
    private String redirectMiniAppUrl;

    public OpenId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OpenId ownerId) {
        this.ownerId = ownerId;
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectMiniAppUrl() {
        return redirectMiniAppUrl;
    }

    public void setRedirectMiniAppUrl(String redirectMiniAppUrl) {
        this.redirectMiniAppUrl = redirectMiniAppUrl;
    }
}
