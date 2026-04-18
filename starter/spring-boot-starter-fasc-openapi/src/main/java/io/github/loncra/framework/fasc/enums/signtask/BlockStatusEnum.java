package io.github.loncra.framework.fasc.enums.signtask;

/**
 * @author Fadada
 * 2021/9/23 11:11:38
 */
public enum BlockStatusEnum {

    BLOCKED("blocked", "阻塞中"),
    UNBLOCKED("unblocked", "未阻塞"),
    ;

    private String code;
    private String remark;

    BlockStatusEnum(
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
