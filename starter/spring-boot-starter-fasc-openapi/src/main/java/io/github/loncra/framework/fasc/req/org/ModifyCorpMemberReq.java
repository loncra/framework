package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author Fadada
 * @date 2022/11/23 17:37
 */
public class ModifyCorpMemberReq extends BaseReq {

    private String openCorpId;
    private Long memberId;
    private String memberName;
    private String internalIdentifier;
    private String memberEmail;
    private String memberMobile;
    private List<Long> memberDeptIds;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getInternalIdentifier() {
        return internalIdentifier;
    }

    public void setInternalIdentifier(String internalIdentifier) {
        this.internalIdentifier = internalIdentifier;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public List<Long> getMemberDeptIds() {
        return memberDeptIds;
    }

    public void setMemberDeptIds(List<Long> memberDeptIds) {
        this.memberDeptIds = memberDeptIds;
    }
}
