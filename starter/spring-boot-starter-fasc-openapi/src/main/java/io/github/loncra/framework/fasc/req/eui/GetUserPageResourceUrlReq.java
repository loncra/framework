package io.github.loncra.framework.fasc.req.eui;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetUserPageResourceUrlReq extends BaseReq {
    private String openCorpId;
    private String clientUserId;
    private GetPageResourceUrlResource resource;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public GetPageResourceUrlResource getResource() {
        return resource;
    }

    public void setResource(GetPageResourceUrlResource resource) {
        this.resource = resource;
    }
}
