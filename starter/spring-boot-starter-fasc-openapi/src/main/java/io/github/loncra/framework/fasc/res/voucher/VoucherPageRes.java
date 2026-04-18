package io.github.loncra.framework.fasc.res.voucher;

import java.util.List;

public class VoucherPageRes {
    private List<GetVoucherTaskListRes> list;
    private Long listPageNo;
    private Long countInPage;
    private Long listPageCount;
    private Long totalCount;

    public List<GetVoucherTaskListRes> getList() {
        return list;
    }

    public void setList(List<GetVoucherTaskListRes> list) {
        this.list = list;
    }

    public Long getListPageNo() {
        return listPageNo;
    }

    public void setListPageNo(Long listPageNo) {
        this.listPageNo = listPageNo;
    }

    public Long getCountInPage() {
        return countInPage;
    }

    public void setCountInPage(Long countInPage) {
        this.countInPage = countInPage;
    }

    public Long getListPageCount() {
        return listPageCount;
    }

    public void setListPageCount(Long listPageCount) {
        this.listPageCount = listPageCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
