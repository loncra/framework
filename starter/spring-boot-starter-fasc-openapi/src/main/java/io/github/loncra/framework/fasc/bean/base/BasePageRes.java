package io.github.loncra.framework.fasc.bean.base;

/**
 * @author Fadada
 * 2021/9/11 15:31:15
 */
public class BasePageRes extends BaseBean {
    private Integer listPageNo;
    private Integer countInPage;
    private Integer listPageCount;
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getListPageCount() {
        return listPageCount;
    }

    public void setListPageCount(Integer listPageCount) {
        this.listPageCount = listPageCount;
    }

    public Integer getListPageNo() {
        return listPageNo;
    }

    public void setListPageNo(Integer listPageNo) {
        this.listPageNo = listPageNo;
    }

    public Integer getCountInPage() {
        return countInPage;
    }

    public void setCountInPage(Integer countInPage) {
        this.countInPage = countInPage;
    }
}
