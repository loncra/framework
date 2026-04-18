package io.github.loncra.framework.fasc.bean.base;

/**
 * @author Fadada
 * 2021/9/11 15:19:05
 */
public class BasePageReq extends BaseReq {
    private Integer listPageNo;
    private Integer listPageSize;

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
