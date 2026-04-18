package io.github.loncra.framework.fasc.req.app;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： zhupintian
 * @Date: 2022/11/29
 */
public class GetAppOpenIdListReq extends BaseReq {
    private String idType;

    private Boolean owner;

    private Integer listPageNo;

    private Integer listPageSize;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
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
}
