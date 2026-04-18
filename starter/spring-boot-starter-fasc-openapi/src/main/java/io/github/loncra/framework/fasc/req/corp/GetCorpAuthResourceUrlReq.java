package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2022/7/19 15:48
 */
public class GetCorpAuthResourceUrlReq extends BaseReq {
    private String clientCorpId;

    private String clientUserId;

    private String accountName;

    private CorpIdentInfoReq corpIdentInfo;

    private List<String> corpNonEditableInfo;

    private OprIdentInfoReq oprIdentInfo;

    private String corpIdentType;

    private String corpName;

    private String corpIdentNo;

    private Boolean corpIdentInfoMatch;

    private List<String> authScopes;

    private String redirectUrl;

    private String redirectMiniAppUrl;

    private List<String> oprNonEditableInfo;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CorpIdentInfoReq getCorpIdentInfo() {
        return corpIdentInfo;
    }

    public void setCorpIdentInfo(CorpIdentInfoReq corpIdentInfo) {
        this.corpIdentInfo = corpIdentInfo;
    }

    public List<String> getCorpNonEditableInfo() {
        return corpNonEditableInfo;
    }

    public void setCorpNonEditableInfo(List<String> corpNonEditableInfo) {
        this.corpNonEditableInfo = corpNonEditableInfo;
    }

    public OprIdentInfoReq getOprIdentInfo() {
        return oprIdentInfo;
    }

    public void setOprIdentInfo(OprIdentInfoReq oprIdentInfo) {
        this.oprIdentInfo = oprIdentInfo;
    }

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
    }

    public String getCorpIdentType() {
        return corpIdentType;
    }

    public void setCorpIdentType(String corpIdentType) {
        this.corpIdentType = corpIdentType;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpIdentNo() {
        return corpIdentNo;
    }

    public void setCorpIdentNo(String corpIdentNo) {
        this.corpIdentNo = corpIdentNo;
    }

    public Boolean getCorpIdentInfoMatch() {
        return corpIdentInfoMatch;
    }

    public void setCorpIdentInfoMatch(Boolean corpIdentInfoMatch) {
        this.corpIdentInfoMatch = corpIdentInfoMatch;
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

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public List<String> getOprNonEditableInfo() {
        return oprNonEditableInfo;
    }

    public void setOprNonEditableInfo(List<String> oprNonEditableInfo) {
        this.oprNonEditableInfo = oprNonEditableInfo;
    }
}
