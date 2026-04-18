package io.github.loncra.framework.fasc.res.org;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class EmployeeInfo extends BaseBean {
    private Long memberId;
    private String memberName;
    private String internalIdentifier;
    private String memberEmail;
    private String memberMobile;
    private String memberStatus;
    private List<Long> memberDeptIds;
    private List<String> roleType;


    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
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

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public List<String> getRoleType() {
        return roleType;
    }

    public void setRoleType(List<String> roleType) {
        this.roleType = roleType;
    }

    public List<Long> getMemberDeptIds() {
        return memberDeptIds;
    }

    public void setMemberDeptIds(List<Long> memberDeptIds) {
        this.memberDeptIds = memberDeptIds;
    }
}
