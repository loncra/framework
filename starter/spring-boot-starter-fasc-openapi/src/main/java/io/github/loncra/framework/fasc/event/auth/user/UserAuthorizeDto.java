package io.github.loncra.framework.fasc.event.auth.user;

/**
 * 个人用户授权事件
 */
public class UserAuthorizeDto {

    private String eventTime;

    private String openUserId;

    private String authResult;

    private String authFailedReason;

    private String authScope;

    private String identProcessStatus;

    private String identMethod;

    private String identFailedReason;

    private String clientUserId;


    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getAuthResult() {
        return authResult;
    }

    public void setAuthResult(String authResult) {
        this.authResult = authResult;
    }

    public String getAuthFailedReason() {
        return authFailedReason;
    }

    public void setAuthFailedReason(String authFailedReason) {
        this.authFailedReason = authFailedReason;
    }

    public String getAuthScope() {
        return authScope;
    }

    public void setAuthScope(String authScope) {
        this.authScope = authScope;
    }

    public String getIdentProcessStatus() {
        return identProcessStatus;
    }

    public void setIdentProcessStatus(String identProcessStatus) {
        this.identProcessStatus = identProcessStatus;
    }

    public String getIdentMethod() {
        return identMethod;
    }

    public void setIdentMethod(String identMethod) {
        this.identMethod = identMethod;
    }

    public String getIdentFailedReason() {
        return identFailedReason;
    }

    public void setIdentFailedReason(String identFailedReason) {
        this.identFailedReason = identFailedReason;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }
}
