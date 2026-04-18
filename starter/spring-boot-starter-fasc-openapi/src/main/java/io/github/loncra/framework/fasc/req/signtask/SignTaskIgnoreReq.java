package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

public class SignTaskIgnoreReq extends BaseReq {

    private String signTaskId;
    private List<Actor> actors;


    public static class Actor {

        private String actorId;
        public String reason;

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
