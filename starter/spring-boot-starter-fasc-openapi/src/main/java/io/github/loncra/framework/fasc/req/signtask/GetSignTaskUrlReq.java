package io.github.loncra.framework.fasc.req.signtask;


import io.github.loncra.framework.fasc.bean.common.OpenId;

import java.io.Serial;
import java.util.List;

public class GetSignTaskUrlReq extends SignTaskBaseReq {
    @Serial
    private static final long serialVersionUID = -3235959868192198879L;

    private OpenId initiator;

    private String redirectUrl;

    private List<String> nonEditableInfo;

    public GetSignTaskUrlReq() {
    }

    public GetSignTaskUrlReq(String signTaskId) {
        super(signTaskId);
    }

    public OpenId getInitiator() {
        return initiator;
    }

    public void setInitiator(OpenId initiator) {
        this.initiator = initiator;
    }

    public List<String> getNonEditableInfo() {
        return nonEditableInfo;
    }

    public void setNonEditableInfo(List<String> nonEditableInfo) {
        this.nonEditableInfo = nonEditableInfo;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
