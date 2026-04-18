package io.github.loncra.framework.fasc.res.signtask;

import java.util.List;

public class CerInfoActor {
    private String actorId;
    private String actorType;
    private String actorName;
    private String identName;
    private List<String> permissions;
    private List<String> cerFile;

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

    public String getIdentName() {
        return identName;
    }

    public void setIdentName(String identName) {
        this.identName = identName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getCerFile() {
        return cerFile;
    }

    public void setCerFile(List<String> cerFile) {
        this.cerFile = cerFile;
    }
}
