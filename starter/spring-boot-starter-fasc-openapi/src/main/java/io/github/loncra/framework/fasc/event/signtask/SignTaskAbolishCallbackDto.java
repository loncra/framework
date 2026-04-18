package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务作废事件
 */
public class SignTaskAbolishCallbackDto extends BaseSignTaskStatusCallbackDto {

    private String abolishedSignTaskId;

    public String getAbolishedSignTaskId() {
        return abolishedSignTaskId;
    }

    public void setAbolishedSignTaskId(String abolishedSignTaskId) {
        this.abolishedSignTaskId = abolishedSignTaskId;
    }
}
