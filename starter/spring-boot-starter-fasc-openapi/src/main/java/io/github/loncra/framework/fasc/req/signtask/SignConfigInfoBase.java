package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

public class SignConfigInfoBase extends BaseBean {
    private Integer orderNo;
    private Boolean blockHere;
    private Boolean requestVerifyFree;
    private Boolean requestMemberSign;
    private List<String> verifyMethods;
    private Boolean joinByLink;
    private String signerSignMethod;
    private Boolean readingToEnd;
    private String readingTime;
    private Boolean identifiedView;
    private Boolean resizeSeal;
    private List<String> audioVideoInfo;
    private List<ActorAttachInfo> actorAttachInfos;
    private Boolean freeLogin;
    private Boolean viewCompletedTask;
    private Long freeDragSealId;
    private Boolean signAllDoc;
    private Boolean authorizeFreeSign;

    public Boolean getAuthorizeFreeSign() {
        return authorizeFreeSign;
    }

    public void setAuthorizeFreeSign(Boolean authorizeFreeSign) {
        this.authorizeFreeSign = authorizeFreeSign;
    }

    public Boolean getRequestMemberSign() {
        return requestMemberSign;
    }

    public void setRequestMemberSign(Boolean requestMemberSign) {
        this.requestMemberSign = requestMemberSign;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getSignerSignMethod() {
        return signerSignMethod;
    }

    public void setSignerSignMethod(String signerSignMethod) {
        this.signerSignMethod = signerSignMethod;
    }

    public Boolean getReadingToEnd() {
        return readingToEnd;
    }

    public void setReadingToEnd(Boolean readingToEnd) {
        this.readingToEnd = readingToEnd;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }

    public Boolean getBlockHere() {
        return blockHere;
    }

    public void setBlockHere(Boolean blockHere) {
        this.blockHere = blockHere;
    }

    public Boolean getRequestVerifyFree() {
        return requestVerifyFree;
    }

    public void setRequestVerifyFree(Boolean requestVerifyFree) {
        this.requestVerifyFree = requestVerifyFree;
    }

    public List<String> getVerifyMethods() {
        return verifyMethods;
    }

    public void setVerifyMethods(List<String> verifyMethods) {
        this.verifyMethods = verifyMethods;
    }

    public Boolean getJoinByLink() {
        return joinByLink;
    }

    public void setJoinByLink(Boolean joinByLink) {
        this.joinByLink = joinByLink;
    }

    public Boolean getIdentifiedView() {
        return identifiedView;
    }

    public void setIdentifiedView(Boolean identifiedView) {
        this.identifiedView = identifiedView;
    }

    public Boolean getResizeSeal() {
        return resizeSeal;
    }

    public void setResizeSeal(Boolean resizeSeal) {
        this.resizeSeal = resizeSeal;
    }

    public List<String> getAudioVideoInfo() {
        return audioVideoInfo;
    }

    public void setAudioVideoInfo(List<String> audioVideoInfo) {
        this.audioVideoInfo = audioVideoInfo;
    }

    public List<ActorAttachInfo> getActorAttachInfos() {
        return actorAttachInfos;
    }

    public void setActorAttachInfos(List<ActorAttachInfo> actorAttachInfos) {
        this.actorAttachInfos = actorAttachInfos;
    }

    public Boolean getFreeLogin() {
        return freeLogin;
    }

    public void setFreeLogin(Boolean freeLogin) {
        this.freeLogin = freeLogin;
    }

    public Boolean getViewCompletedTask() {
        return viewCompletedTask;
    }

    public void setViewCompletedTask(Boolean viewCompletedTask) {
        this.viewCompletedTask = viewCompletedTask;
    }

    public Long getFreeDragSealId() {
        return freeDragSealId;
    }

    public void setFreeDragSealId(Long freeDragSealId) {
        this.freeDragSealId = freeDragSealId;
    }

    public Boolean getSignAllDoc() {
        return signAllDoc;
    }

    public void setSignAllDoc(Boolean signAllDoc) {
        this.signAllDoc = signAllDoc;
    }
}
