package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务延期事件
 */
public class SignTaskExtensionCallbackDto extends BaseSignTaskStatusCallbackDto {

    private String expiresTime;

    private String userName;

    public String getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
