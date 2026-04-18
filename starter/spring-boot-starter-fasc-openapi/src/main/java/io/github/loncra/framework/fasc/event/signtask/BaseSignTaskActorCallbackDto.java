package io.github.loncra.framework.fasc.event.signtask;

public class BaseSignTaskActorCallbackDto {

    private String eventTime;

    private String signTaskId;

    private String actorId;

    private String signTaskStatus;

    private String userName;

    private String transReferenceId;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getSignTaskStatus() {
        return signTaskStatus;
    }

    public void setSignTaskStatus(String signTaskStatus) {
        this.signTaskStatus = signTaskStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }
}
