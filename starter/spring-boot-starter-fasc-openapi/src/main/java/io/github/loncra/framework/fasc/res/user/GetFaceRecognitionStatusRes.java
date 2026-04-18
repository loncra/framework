package io.github.loncra.framework.fasc.res.user;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2023/8/9 10:36
 */
public class GetFaceRecognitionStatusRes {
    private String userName;
    private String userIdentNo;
    private String faceAuthMode;
    private Integer resultStatus;
    private String resultTime;
    private String failedReason;
    private Integer urlStatus;
    private String similarity;
    private String liveRate;
    private String faceImg;
    private List<String> faceImgs;

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

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getLiveRate() {
        return liveRate;
    }

    public void setLiveRate(String liveRate) {
        this.liveRate = liveRate;
    }

    public String getFaceImg() {
        return faceImg;
    }

    public void setFaceImg(String faceImg) {
        this.faceImg = faceImg;
    }

    public List<String> getFaceImgs() {
        return faceImgs;
    }

    public void setFaceImgs(List<String> faceImgs) {
        this.faceImgs = faceImgs;
    }
}
