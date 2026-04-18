package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class GetSealGrantUrlReq extends BaseReq {

    private String openCorpId;
    private Long sealId;
    private MemberInfo memberInfo;
    private String clientUserId;
    private String redirectUrl;

    public static class MemberInfo {
        private List<Long> memberIds;
        private String grantStartTime;
        private String grantEndTime;

        public List<Long> getMemberIds() {
            return memberIds;
        }

        public void setMemberIds(List<Long> memberIds) {
            this.memberIds = memberIds;
        }

        public String getGrantStartTime() {
            return grantStartTime;
        }

        public void setGrantStartTime(String grantStartTime) {
            this.grantStartTime = grantStartTime;
        }

        public String getGrantEndTime() {
            return grantEndTime;
        }

        public void setGrantEndTime(String grantEndTime) {
            this.grantEndTime = grantEndTime;
        }
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public Long getSealId() {
        return sealId;
    }

    public void setSealId(Long sealId) {
        this.sealId = sealId;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}



