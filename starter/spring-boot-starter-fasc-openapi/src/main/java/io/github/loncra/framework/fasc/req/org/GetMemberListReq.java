package io.github.loncra.framework.fasc.req.org;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetMemberListReq extends BaseReq {

    private String ownerId;
    private String openCorpId;
    private GetMemberListFilter listFilter;
    private Integer listPageNo;
    private Integer listPageSize;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public GetMemberListFilter getListFilter() {
        return listFilter;
    }

    public void setListFilter(GetMemberListFilter listFilter) {
        this.listFilter = listFilter;
    }

    public Integer getListPageNo() {
        return listPageNo;
    }

    public void setListPageNo(Integer listPageNo) {
        this.listPageNo = listPageNo;
    }

    public Integer getListPageSize() {
        return listPageSize;
    }

    public void setListPageSize(Integer listPageSize) {
        this.listPageSize = listPageSize;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }
}
