package io.github.loncra.framework.fasc.event.voucher;

/**
 * 填签任务参与方签署成功事件
 */
public class VoucherSignTaskSignedCallBackDto {

    private String eventTime;

    private String signTaskId;

    private String signTaskStatus;

    private String actorId;

    private String actorName;

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

    public String getSignTaskStatus() {
        return signTaskStatus;
    }

    public void setSignTaskStatus(String signTaskStatus) {
        this.signTaskStatus = signTaskStatus;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }
}
