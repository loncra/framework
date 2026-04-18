package io.github.loncra.framework.fasc.event.recognition;


/**
 * （人脸核身）个人身份核验
 */
public class FaceRecognizeCallBackDTO {
    private String eventTime;
    private String userName;
    private String userIdentNo;
    private String faceAuthMode;
    private Integer resultStatus;
    private String resultTime;
    private String failedReason;
    private Integer urlStatus;
    private String serialNo;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdentNo() {
        return userIdentNo;
    }

    public void setUserIdentNo(String userIdentNo) {
        this.userIdentNo = userIdentNo;
    }

    public String getFaceAuthMode() {
        return faceAuthMode;
    }

    public void setFaceAuthMode(String faceAuthMode) {
        this.faceAuthMode = faceAuthMode;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Integer getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(Integer urlStatus) {
        this.urlStatus = urlStatus;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
