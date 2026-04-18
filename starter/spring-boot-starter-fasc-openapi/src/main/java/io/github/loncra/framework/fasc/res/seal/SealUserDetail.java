package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class SealUserDetail extends BaseBean {
    /**
     * 企业成员ID
     */
    private Long memberId;

    /**
     * 成员名称
     */
    private String memberName;

    /**
     * 成员在企业内部的标识符，如工号等，方便和业务系统对应
     */
    private String internalIdentifier;

    /**
     * 企业成员邮箱
     */
    private String memberEmail;

    /**
     * 操作授权的时间
     */
    private String grantTime;

    /**
     * 该印章被授权用印员的生效时间，如授权长期有效则返回null
     */
    private String grantStartTime;

    /**
     * 该印章被授权用印员的失效时间，如授权长期有效则返回null
     */
    private String grantEndTime;

    /**
     * 授权状态：effective-生效中，ineffective-已失效
     */
    private String grantStatus;

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

    public String getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(String grantTime) {
        this.grantTime = grantTime;
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

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }
}