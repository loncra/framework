package io.github.loncra.framework.fasc.res.event;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author Fadada
 * @date 2021/11/16 17:48:28
 */
public class SignTaskSignFailedInfo extends BaseBean {
    private String eventTime;
    private String signTaskId;
    private String signTaskStatus;
    private String actorId;
    private OpenId actorOpenId;
    private Boolean verifyFreeSign;
    private String signFailedReason;

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

    public OpenId getActorOpenId() {
        return actorOpenId;
    }

    public void setActorOpenId(OpenId actorOpenId) {
        this.actorOpenId = actorOpenId;
    }

    public Boolean getVerifyFreeSign() {
        return verifyFreeSign;
    }

    public void setVerifyFreeSign(Boolean verifyFreeSign) {
        this.verifyFreeSign = verifyFreeSign;
    }

    public String getSignFailedReason() {
        return signFailedReason;
    }

    public void setSignFailedReason(String signFailedReason) {
        this.signFailedReason = signFailedReason;
    }
}
