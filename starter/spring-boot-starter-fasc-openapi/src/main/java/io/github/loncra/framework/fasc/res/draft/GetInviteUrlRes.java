package io.github.loncra.framework.fasc.res.draft;

public class GetInviteUrlRes {

    private String contractName;

    private String createdByOwner;

    private String createdByName;

    private String inviteLinkUrl;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCreatedByOwner() {
        return createdByOwner;
    }

    public void setCreatedByOwner(String createdByOwner) {
        this.createdByOwner = createdByOwner;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getInviteLinkUrl() {
        return inviteLinkUrl;
    }

    public void setInviteLinkUrl(String inviteLinkUrl) {
        this.inviteLinkUrl = inviteLinkUrl;
    }
}
