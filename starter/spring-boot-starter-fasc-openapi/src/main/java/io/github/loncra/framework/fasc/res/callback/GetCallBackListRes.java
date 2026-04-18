package io.github.loncra.framework.fasc.res.callback;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2023/6/14 14:16
 */
public class GetCallBackListRes {
    private List<CallBackInfo> callbackInfos;
    private Integer listPageNo;
    private Integer countInPage;
    private Integer listPageCount;
    private Integer totalCount;

    public List<CallBackInfo> getCallbackInfos() {
        return callbackInfos;
    }

    public void setCallbackInfos(List<CallBackInfo> callbackInfos) {
        this.callbackInfos = callbackInfos;
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
