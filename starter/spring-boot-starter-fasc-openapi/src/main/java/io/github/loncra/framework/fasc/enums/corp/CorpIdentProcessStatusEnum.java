package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/23 16:15:39
 */
public enum CorpIdentProcessStatusEnum {
    /**
     * 企业实名认证状态：
     * no_start: 未认证
     * identifying: 认证中
     * checking: 审核中
     * checked: 初审通过
     * success: 认证通过
     * failed: 认证不通过
     */

    NO_START("no_start", "未认证"),
    IDENTIFYING("identifying", "认证中"),
    CHECKING("checking", "审核中"),
    CHECKED("checked", "初审通过"),
    SUCCESS("success", "认证通过"),
    FAILED("failed", "认证不通过");

    private String code;
    private String remark;

    CorpIdentProcessStatusEnum(
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
