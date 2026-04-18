package io.github.loncra.framework.fasc.enums.signtask;

/**
 * @author Fadada
 * 2021/9/23 11:11:38
 */
public enum SignStatusEnum {

    WAIT_SIGN("wait_sign", "待签署"),
    SIGNED("signed", "已签署"),
    SIGN_REJECTED("sign_rejected", "已拒签"),
    ;

    private String code;
    private String remark;

    SignStatusEnum(
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
