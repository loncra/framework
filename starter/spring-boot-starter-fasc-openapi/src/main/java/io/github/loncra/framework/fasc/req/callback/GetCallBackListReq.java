package io.github.loncra.framework.fasc.req.callback;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author zhoufucheng
 * @date 2023/6/14 14:13
 */
public class GetCallBackListReq extends BaseReq {
    private String callbackType;
    private String startTime;
    private String endTime;
    private Integer listPageNo;
    private Integer listPageSize;

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
