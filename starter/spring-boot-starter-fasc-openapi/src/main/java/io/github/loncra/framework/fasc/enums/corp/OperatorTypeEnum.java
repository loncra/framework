package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/21 12:18:00
 */
public enum OperatorTypeEnum {
    /**
     * 企业认证经办人身份类型：
     * legal_rep: 企业法定代表人
     * deputy_auth: 企业授权代理人
     */
    LEGAL_REP("legal_rep", "企业法定代表人"),
    DEPUTY_AUTH("deputy_auth", "企业普通代理人");

    private String code;
    private String remark;

    OperatorTypeEnum(
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
