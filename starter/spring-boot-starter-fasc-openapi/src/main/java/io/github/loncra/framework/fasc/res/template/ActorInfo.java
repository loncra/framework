package io.github.loncra.framework.fasc.res.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/6/22
 */
public class ActorInfo extends BaseBean {

    private String actorId;

    private String actorType;

    @JsonProperty("isInitiator")
    private Boolean isInitiator;

    private List<String> permissions;

    private String identNameForMatch;

    private String certType;

    private String certNoForMatch;

    private List<Long> memberIds;


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

    public String getIdentNameForMatch() {
        return identNameForMatch;
    }

    public void setIdentNameForMatch(String identNameForMatch) {
        this.identNameForMatch = identNameForMatch;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNoForMatch() {
        return certNoForMatch;
    }

    public void setCertNoForMatch(String certNoForMatch) {
        this.certNoForMatch = certNoForMatch;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }
}
