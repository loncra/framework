package io.github.loncra.framework.fasc.enums.signtask;

/**
 * @author Fadada
 * 2021/9/23 11:11:38
 */
public enum FillStatusEnum {

    WAIT_FILL("wait_fill", "待填写"),
    FILLED("filled", "已填写"),
    FILL_REJECTED("fill_rejected", "已拒填"),
    ;

    private String code;
    private String remark;

    FillStatusEnum(
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
