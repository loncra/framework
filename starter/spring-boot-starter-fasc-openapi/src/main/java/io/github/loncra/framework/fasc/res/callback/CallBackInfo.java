package io.github.loncra.framework.fasc.res.callback;

/**
 * @author zhoufucheng
 * @date 2023/6/7 14:10
 */
public class CallBackInfo {
    private String eventId;
    private String eventInfo;
    private Boolean success;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
