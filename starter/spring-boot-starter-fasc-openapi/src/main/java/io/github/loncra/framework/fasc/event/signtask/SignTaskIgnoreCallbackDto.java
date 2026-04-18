package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务驳回填写事件
 */
public class SignTaskIgnoreCallbackDto extends BaseSignTaskStatusCallbackDto {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
