package io.github.loncra.framework.fasc.event.voucher;

/**
 * 填签签署任务完成事件
 */
public class VoucherSignTaskFinishedCallBackDto {

    private String eventTime;

    private String signTaskId;

    private String signTaskStatus;

    private String transReferenceId;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getSignTaskStatus() {
        return signTaskStatus;
    }

    public void setSignTaskStatus(String signTaskStatus) {
        this.signTaskStatus = signTaskStatus;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }
}
