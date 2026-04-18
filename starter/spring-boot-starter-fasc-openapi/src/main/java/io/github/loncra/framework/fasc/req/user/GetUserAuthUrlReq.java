package io.github.loncra.framework.fasc.req.user;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author Fadada
 * 2021/10/16 17:38:58
 */
public class GetUserAuthUrlReq extends BaseReq {
    private String clientUserId;
    private List<String> authScopes;
    private String redirectUrl;
    private String redirectMiniAppUrl;
    private String accountName;
    private UserIdentInfoReq userIdentInfo;
    private List<String> nonEditableInfo;
    private Boolean unbindAccount;
    private FreeSignInfoReq freeSignInfo;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public UserIdentInfoReq getUserIdentInfo() {
        return userIdentInfo;
    }

    public void setUserIdentInfo(UserIdentInfoReq userIdentInfo) {
        this.userIdentInfo = userIdentInfo;
    }

    public List<String> getNonEditableInfo() {
        return nonEditableInfo;
    }

    public void setNonEditableInfo(List<String> nonEditableInfo) {
        this.nonEditableInfo = nonEditableInfo;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public List<String> getAuthScopes() {
        return authScopes;
    }

    public void setAuthScopes(List<String> authScopes) {
        this.authScopes = authScopes;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectMiniAppUrl() {
        return redirectMiniAppUrl;
    }

    public void setRedirectMiniAppUrl(String redirectMiniAppUrl) {
        this.redirectMiniAppUrl = redirectMiniAppUrl;
    }

    public Boolean getUnbindAccount() {
        return unbindAccount;
    }

    public void setUnbindAccount(Boolean unbindAccount) {
        this.unbindAccount = unbindAccount;
    }

    public FreeSignInfoReq getFreeSignInfo() {
        return freeSignInfo;
    }

    public void setFreeSignInfo(FreeSignInfoReq freeSignInfo) {
        this.freeSignInfo = freeSignInfo;
    }
}
