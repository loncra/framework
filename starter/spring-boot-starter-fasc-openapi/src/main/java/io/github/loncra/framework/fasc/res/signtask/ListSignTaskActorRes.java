package io.github.loncra.framework.fasc.res.signtask;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Notification;

import java.util.List;

/**
 * @author hukc
 * @date 2022年10月31日
 */
public class ListSignTaskActorRes extends BaseBean {
    private String actorId;
    private String actorType;
    private String actorName;
    private List<String> permissions;
    @JsonProperty("isInitiator")
    private Boolean isInitiator;
    private Integer signOrderNo;
    private String identNameForMatch;
    private String certNoForMatch;
    private String joinStatus;
    private String joinTime;
    private String joinName;
    private String joinIdentNo;
    private String fillStatus;
    private String fillTime;
    private String signStatus;
    private String actorNote;
    private String signTime;
    private String blockStatus;
    private Notification notification;
    private String readStatus;
    private String readTime;
    private String verifyMethod;
    private String confirmationTime;
    private SignTaskActorCorpMemberRes memberInfo;

    public static class SignTaskActorCorpMemberRes {
        private String memberName;
        private String mobile;
        private String memberId;

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }
    }

    public SignTaskActorCorpMemberRes getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(SignTaskActorCorpMemberRes memberInfo) {
        this.memberInfo = memberInfo;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public void setActorType(String actorType) {
        this.actorType = actorType;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public void setInitiator(Boolean initiator) {
        isInitiator = initiator;
    }

    public void setSignOrderNo(Integer signOrderNo) {
        this.signOrderNo = signOrderNo;
    }

    public void setIdentNameForMatch(String identNameForMatch) {
        this.identNameForMatch = identNameForMatch;
    }

    public void setCertNoForMatch(String certNoForMatch) {
        this.certNoForMatch = certNoForMatch;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public void setJoinIdentNo(String joinIdentNo) {
        this.joinIdentNo = joinIdentNo;
    }

    public void setFillStatus(String fillStatus) {
        this.fillStatus = fillStatus;
    }

    public void setFillTime(String fillTime) {
        this.fillTime = fillTime;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public void setActorNote(String actorNote) {
        this.actorNote = actorNote;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public void setBlockStatus(String blockStatus) {
        this.blockStatus = blockStatus;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getActorId() {
        return actorId;
    }

    public String getActorType() {
        return actorType;
    }

    public String getActorName() {
        return actorName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public Boolean getInitiator() {
        return isInitiator;
    }

    public Integer getSignOrderNo() {
        return signOrderNo;
    }

    public String getIdentNameForMatch() {
        return identNameForMatch;
    }

    public String getCertNoForMatch() {
        return certNoForMatch;
    }

    public String getJoinStatus() {
        return joinStatus;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public String getJoinName() {
        return joinName;
    }

    public String getJoinIdentNo() {
        return joinIdentNo;
    }

    public String getFillStatus() {
        return fillStatus;
    }

    public String getFillTime() {
        return fillTime;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public String getActorNote() {
        return actorNote;
    }

    public String getSignTime() {
        return signTime;
    }

    public String getBlockStatus() {
        return blockStatus;
    }

    public Notification getNotification() {
        return notification;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getVerifyMethod() {
        return verifyMethod;
    }

    public void setVerifyMethod(String verifyMethod) {
        this.verifyMethod = verifyMethod;
    }

    public String getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(String confirmationTime) {
        this.confirmationTime = confirmationTime;
    }
}
