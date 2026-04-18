package io.github.loncra.framework.fasc.res.org;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/13
 */
public class GetMemberListRes extends BaseBean {
    private List<EmployeeInfo> employeeInfos;
    private Integer listPageNo;
    private Integer countInPage;
    private Integer listPageCount;
    private Integer totalCount;

    public List<EmployeeInfo> getEmployeeInfos() {
        return employeeInfos;
    }

    public void setEmployeeInfos(List<EmployeeInfo> employeeInfos) {
        this.employeeInfos = employeeInfos;
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
