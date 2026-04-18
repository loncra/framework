package io.github.loncra.framework.fasc.res.org;

/**
 * @author Fadada
 * @date 2022/11/23 17:49
 */
public class SimpleEmployeeInfo {

    private Long memberId;
    private String memberActiveUrl;
    private String memberActiveEmbedUrl;
    private String internalIdentifier;
    private String memberStatus;

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberActiveUrl() {
        return memberActiveUrl;
    }

    public void setMemberActiveUrl(String memberActiveUrl) {
        this.memberActiveUrl = memberActiveUrl;
    }

    public String getMemberActiveEmbedUrl() {
        return memberActiveEmbedUrl;
    }

    public void setMemberActiveEmbedUrl(String memberActiveEmbedUrl) {
        this.memberActiveEmbedUrl = memberActiveEmbedUrl;
    }

    public String getInternalIdentifier() {
        return internalIdentifier;
    }

    public void setInternalIdentifier(String internalIdentifier) {
        this.internalIdentifier = internalIdentifier;
    }
}
