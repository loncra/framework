package io.github.loncra.framework.fasc.res.signtask;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/13 16:53:59
 */
public class SignTaskDetailActorInfo extends BaseBean {
    private String actorId;
    private String actorType;
    private String actorName;
    private String actorOpenId;
    private List<String> permissions;
    @JsonProperty("isInitiator")
    private Boolean isInitiator;

    public String getActorOpenId() {
        return actorOpenId;
    }

    public void setActorOpenId(String actorOpenId) {
        this.actorOpenId = actorOpenId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorType() {
        return actorType;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Boolean getInitiator() {
        return isInitiator;
    }

    public void setInitiator(Boolean initiator) {
        isInitiator = initiator;
    }
}
