package io.github.loncra.framework.fasc.event.signtask;

/**
 * @author zhoufucheng
 * @date 2023/12/6 9:57
 */
public class SignTaskCreatedCallbackDto {
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
