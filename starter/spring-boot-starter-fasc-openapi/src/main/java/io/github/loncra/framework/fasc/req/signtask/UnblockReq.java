package io.github.loncra.framework.fasc.req.signtask;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class UnblockReq extends SignTaskBaseReq {
    private String actorId;

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

}
