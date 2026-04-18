package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/13 16:44:09
 */
public class GetSignTaskListRes extends BaseBean {
    private List<SignTaskInfo> signTasks;
    private Integer listPageNo;
    private Integer countInPage;
    private Integer listPageCount;
    private Integer totalCount;

    public List<SignTaskInfo> getSignTasks() {
        return signTasks;
    }

    public void setSignTasks(List<SignTaskInfo> signTasks) {
        this.signTasks = signTasks;
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

    public Integer getListPageCount() {
        return listPageCount;
    }

    public void setListPageCount(Integer listPageCount) {
        this.listPageCount = listPageCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
