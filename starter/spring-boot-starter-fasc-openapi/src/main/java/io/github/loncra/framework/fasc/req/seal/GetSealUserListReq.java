package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetSealUserListReq extends BaseReq {

    private String ownerId;
    private String openCorpId;
    private GetSealUserListFilter listFilter;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public GetSealUserListFilter getListFilter() {
        return listFilter;
    }

    public void setListFilter(GetSealUserListFilter listFilter) {
        this.listFilter = listFilter;
    }
}
