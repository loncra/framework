package io.github.loncra.framework.fasc.req.signtask;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class GetUrlReq extends SignTaskBaseReq {
    private String actorType;
    private String actorId;
    private String redirectUrl;

    public String getActorType() {
        return actorType;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
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
