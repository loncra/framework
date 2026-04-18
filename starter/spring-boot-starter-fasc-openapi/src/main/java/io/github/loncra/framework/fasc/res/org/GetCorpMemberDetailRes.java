package io.github.loncra.framework.fasc.res.org;

import java.util.List;

/**
 * @author Fadada
 * @date 2022/11/23 17:48
 */
public class GetCorpMemberDetailRes {

    private Long memberId;
    private String memberName;
    private String internalIdentifier;
    private String memberEmail;
    private String memberMobile;
    private String fddId;
    private String memberStatus;
    private List<Long> memberDeptIds;
    private List<String> roleType;

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

    public String getFddId() {
        return fddId;
    }

    public void setFddId(String fddId) {
        this.fddId = fddId;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public List<Long> getMemberDeptIds() {
        return memberDeptIds;
    }

    public void setMemberDeptIds(List<Long> memberDeptIds) {
        this.memberDeptIds = memberDeptIds;
    }

    public List<String> getRoleType() {
        return roleType;
    }

    public void setRoleType(List<String> roleType) {
        this.roleType = roleType;
    }
}
