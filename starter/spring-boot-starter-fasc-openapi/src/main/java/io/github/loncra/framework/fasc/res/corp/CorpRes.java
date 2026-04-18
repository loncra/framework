package io.github.loncra.framework.fasc.res.corp;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 14:07:49
 */
public class CorpRes extends BaseBean {
    private String clientCorpId;
    private String clientCorpName;
    private String bindingStatus;
    private List<String> authScope;
    private String identStatus;
    private String availableStatus;
    private String openCorpId;

    public String getClientCorpId() {
        return clientCorpId;
    }

    public void setClientCorpId(String clientCorpId) {
        this.clientCorpId = clientCorpId;
    }

    public String getClientCorpName() {
        return clientCorpName;
    }

    public void setClientCorpName(String clientCorpName) {
        this.clientCorpName = clientCorpName;
    }

    public String getBindingStatus() {
        return bindingStatus;
    }

    public void setBindingStatus(String bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public List<String> getAuthScope() {
        return authScope;
    }

    public void setAuthScope(List<String> authScope) {
        this.authScope = authScope;
    }

    public String getIdentStatus() {
        return identStatus;
    }

    public void setIdentStatus(String identStatus) {
        this.identStatus = identStatus;
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(String availableStatus) {
        this.availableStatus = availableStatus;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}
