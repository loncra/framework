package io.github.loncra.framework.fasc.res.event;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * 2021/10/21 15:49:48
 */
public class CorpAuthorizedInfo extends BaseBean {
    private String eventTime;
    private String openCorpId;
    private String operatorId;
    private String authResult;
    private String authFailedReason;
    private List<String> authScope;
    private String corpIdentProcessStatus;
    private String corpIdentMethod;
    private String corpIdentFailedReason;
    private String operatorIdentProcessStatus;
    private String operatorIdentMethod;
    private String operatorIdentFailedReason;
    private String clientCorpId;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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

    public List<String> getAuthScope() {
        return authScope;
    }

    public void setAuthScope(List<String> authScope) {
        this.authScope = authScope;
    }

    public String getCorpIdentProcessStatus() {
        return corpIdentProcessStatus;
    }

    public void setCorpIdentProcessStatus(String corpIdentProcessStatus) {
        this.corpIdentProcessStatus = corpIdentProcessStatus;
    }

    public String getCorpIdentMethod() {
        return corpIdentMethod;
    }

    public void setCorpIdentMethod(String corpIdentMethod) {
        this.corpIdentMethod = corpIdentMethod;
    }

    public String getCorpIdentFailedReason() {
        return corpIdentFailedReason;
    }

    public void setCorpIdentFailedReason(String corpIdentFailedReason) {
        this.corpIdentFailedReason = corpIdentFailedReason;
    }

    public String getOperatorIdentProcessStatus() {
        return operatorIdentProcessStatus;
    }

    public void setOperatorIdentProcessStatus(String operatorIdentProcessStatus) {
        this.operatorIdentProcessStatus = operatorIdentProcessStatus;
    }

    public String getOperatorIdentMethod() {
        return operatorIdentMethod;
    }

    public void setOperatorIdentMethod(String operatorIdentMethod) {
        this.operatorIdentMethod = operatorIdentMethod;
    }

    public String getOperatorIdentFailedReason() {
        return operatorIdentFailedReason;
    }

    public void setOperatorIdentFailedReason(String operatorIdentFailedReason) {
        this.operatorIdentFailedReason = operatorIdentFailedReason;
    }

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
    }
}

