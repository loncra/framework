package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务待处理事件（API3.0任务专属）
 */
public class SignTaskPendingCallbackDto {

    private String eventTime;

    private String signTaskId;

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
}
