package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class DeleteActorReq extends SignTaskBaseReq {
    private List<String> actorIds;

    public List<String> getActorIds() {
        return actorIds;
    }

    public void setActorIds(List<String> actorIds) {
        this.actorIds = actorIds;
    }
}
