package io.github.loncra.framework.fasc.event.auth.corp;

/**
 * 企业解除授权
 */
public class CorpCancelAuthorizationCallBackDto {

    private String eventTime;
    private String clientCorpId;
    private String openCorpId;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}
