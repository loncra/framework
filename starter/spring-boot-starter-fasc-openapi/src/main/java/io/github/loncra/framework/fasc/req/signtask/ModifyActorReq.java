package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author fadada
 * @date 2023/7/13 10:26
 */
public class ModifyActorReq extends BaseReq {

    private String signTaskId;

    private String businessId;

    private List<ModifySignTaskActorInfo> actors;

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public List<ModifySignTaskActorInfo> getActors() {
        return actors;
    }

    public void setActors(List<ModifySignTaskActorInfo> actors) {
        this.actors = actors;
    }

    public static class ModifySignTaskActorInfo {

        private String actorId;

        private List<AddFillFieldInfo> fillFields;

        private List<AddSignFieldInfo> signFields;

        private ModifySignConfigInfo signConfigInfo;

        public String getActorId() {
            return actorId;
        }

        public void setActorId(String actorId) {
            this.actorId = actorId;
        }

        public List<AddFillFieldInfo> getFillFields() {
            return fillFields;
        }

        public void setFillFields(List<AddFillFieldInfo> fillFields) {
            this.fillFields = fillFields;
        }

        public List<AddSignFieldInfo> getSignFields() {
            return signFields;
        }

        public void setSignFields(List<AddSignFieldInfo> signFields) {
            this.signFields = signFields;
        }

        public ModifySignConfigInfo getSignConfigInfo() {
            return signConfigInfo;
        }

        public void setSignConfigInfo(ModifySignConfigInfo signConfigInfo) {
            this.signConfigInfo = signConfigInfo;
        }
    }

    public static class ModifySignConfigInfo {

        private Boolean requestVerifyFree;

        private String signerSignMethod;

        private Boolean readingToEnd;

        private Integer readingTime;

        private Boolean requestMemberSign;

        private Boolean resizeSeal;

        private Long freeDragSealId;

        private Boolean authorizeFreeSign;

        public Boolean getAuthorizeFreeSign() {
            return authorizeFreeSign;
        }

        public void setAuthorizeFreeSign(Boolean authorizeFreeSign) {
            this.authorizeFreeSign = authorizeFreeSign;
        }

        public Long getFreeDragSealId() {
            return freeDragSealId;
        }

        public void setFreeDragSealId(Long freeDragSealId) {
            this.freeDragSealId = freeDragSealId;
        }

        public Boolean getRequestVerifyFree() {
            return requestVerifyFree;
        }

        public void setRequestVerifyFree(Boolean requestVerifyFree) {
            this.requestVerifyFree = requestVerifyFree;
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

        public Integer getReadingTime() {
            return readingTime;
        }

        public void setReadingTime(Integer readingTime) {
            this.readingTime = readingTime;
        }

        public Boolean getRequestMemberSign() {
            return requestMemberSign;
        }

        public void setRequestMemberSign(Boolean requestMemberSign) {
            this.requestMemberSign = requestMemberSign;
        }

        public Boolean getResizeSeal() {
            return resizeSeal;
        }

        public void setResizeSeal(Boolean resizeSeal) {
            this.resizeSeal = resizeSeal;
        }
    }
}
