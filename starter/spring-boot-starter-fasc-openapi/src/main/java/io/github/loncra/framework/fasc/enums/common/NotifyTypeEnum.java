package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * 2021/9/23 11:20:11
 */
public enum NotifyTypeEnum {
    START("start", "发送待填待签通知"),
    FINISH("finish", "发送签署完成通知"),
    CC("cc", "抄送方通知");

    private String code;
    private String remark;

    NotifyTypeEnum(
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
