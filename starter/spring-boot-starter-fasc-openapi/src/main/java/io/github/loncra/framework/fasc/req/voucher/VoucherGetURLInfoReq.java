package io.github.loncra.framework.fasc.req.voucher;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class VoucherGetURLInfoReq extends BaseReq {

    private String signTaskId;

    private String actorId;

    private String redirectUrl;

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
