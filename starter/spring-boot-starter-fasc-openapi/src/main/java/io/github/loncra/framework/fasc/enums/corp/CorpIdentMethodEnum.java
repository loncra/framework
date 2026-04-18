package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/23 16:19:25
 */
public enum CorpIdentMethodEnum {
    /**
     * 企业实名认证时所选择的认证方案：
     * legal_rep: 法定代表人身份认证 (企业经办人为法定代表人时，可选法定代表人身份认证)
     * deputy_auth: 法定代表人授权认证 (企业经办人为代理人时，可选法定代表人授权认证)
     * payment: 企业对公账户认证 (使用企业在银行开立的对公账户验证并通过打款验证其关系)
     * offline: 纸质材料审核认证 (企业线上提供资料提交进行预审，并在线下提供材料进行审核，周期较长)。
     */

    LEGAL_REP("legal_rep", "法定代表人身份认证"),
    DEPUTY_AUTH("deputy_auth", "法定代表人授权认证"),
    PAYMENT("payment", "企业对公账户认证"),
    OFFLINE("offline", "纸质材料审核认证");

    private String code;
    private String remark;

    CorpIdentMethodEnum(
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
