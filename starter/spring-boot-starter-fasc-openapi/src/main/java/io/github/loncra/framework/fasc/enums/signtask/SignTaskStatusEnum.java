package io.github.loncra.framework.fasc.enums.signtask;

/**
 * @author Fadada
 * 2021/9/23 11:11:38
 */
public enum SignTaskStatusEnum {

    TASK_CREATED("task_created", "任务已创建"),
    FILL_PROGRESS("fill_progress", "填写进行中"),
    FILL_COMPLETED("fill_completed", "填写已完成"),
    SIGN_PROGRESS("sign_progress", "签署进行中"),
    SIGN_COMPLETED("sign_completed", "签署已完成"),
    TASK_FINISHED("task_finished", "任务已结束"),
    TASK_TERMINATED("task_terminated", "任务异常停止"),
    TASK_ABOLISHING("abolishing", "作废中"),
    TASK_REVOKED("revoked", "任务已作废"),
    ;

    private String code;
    private String remark;

    SignTaskStatusEnum(
            String code,
            String remark
    ) {
        this.code = code;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
