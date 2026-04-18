package io.github.loncra.framework.fasc.req.signtask;

public class SignTaskActorGetUrlReq extends SignTaskBaseReq {
    private String actorId;
    private String clientUserId;
    private String redirectMiniAppUrl;
    private String redirectUrl;

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
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

    public String getRedirectMiniAppUrl() {
        return redirectMiniAppUrl;
    }

    public void setRedirectMiniAppUrl(String redirectMiniAppUrl) {
        this.redirectMiniAppUrl = redirectMiniAppUrl;
    }
}
