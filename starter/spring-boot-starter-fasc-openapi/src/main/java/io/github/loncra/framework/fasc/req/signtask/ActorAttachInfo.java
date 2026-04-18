package io.github.loncra.framework.fasc.req.signtask;

public class ActorAttachInfo {

    private String actorAttachName;

    private Boolean required = false;

    public String getActorAttachName() {
        return actorAttachName;
    }

    public void setActorAttachName(String actorAttachName) {
        this.actorAttachName = actorAttachName;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
