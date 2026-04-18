package io.github.loncra.framework.fasc.enums.signtask;

public enum SignFieldStatusEnum {
    STAMPED("stamped", "已盖章"),
    UN_STAMPED("unstamped", "未盖章");

    private String code;
    private String remark;

    SignFieldStatusEnum(
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
