package io.github.loncra.framework.fasc.event.signtask;


/**
 * 签署任务撤销事件
 */
public class SignTaskCanceledCallbackDto extends BaseSignTaskStatusCallbackDto {

    private String userName;

    private String terminationNote;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTerminationNote() {
        return terminationNote;
    }

    public void setTerminationNote(String terminationNote) {
        this.terminationNote = terminationNote;
    }
}
