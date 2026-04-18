package io.github.loncra.framework.fasc.enums.signtask;

/**
 * @author Fadada
 * 2021/9/23 11:03:29
 */
public enum JoinStatusEnum {

    NO_JOIN("no_join", "未加入"),
    JOINED("joined", "已加入");

    private String code;
    private String remark;

    JoinStatusEnum(
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
