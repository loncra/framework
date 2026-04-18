package io.github.loncra.framework.fasc.event.auth.user;

/**
 * 个人用户解除授权事件
 */
public class UserCancelAuthorizationDto {

    private String eventTime;
    private String clientUserId;
    private String openUserId;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }
}
