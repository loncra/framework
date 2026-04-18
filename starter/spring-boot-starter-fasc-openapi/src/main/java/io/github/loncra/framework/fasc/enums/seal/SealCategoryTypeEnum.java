package io.github.loncra.framework.fasc.enums.seal;

/**
 * @date 2022年07月03日 23:47
 */
public enum SealCategoryTypeEnum {
    OFFICIAL_SEAL("official_seal", "法定名称章"),
    CONTRACT_SEAL("contract_seal", "合同专用章"),
    HR_SEAL("hr_seal", "人事专用章"),
    FINANCIAL_SEAL("financial_seal", "财务专用章"),
    LEGAL_REPRESENTATIVE_SEAL("legal_representative_seal", "法定代表人名章"),
    OTHER("other", "其他"),
    ;

    private String categoryType;
    private String desc;

    SealCategoryTypeEnum(
            String categoryType,
            String desc
    ) {
        this.categoryType = categoryType;
        this.desc = desc;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public String getDesc() {
        return desc;
    }
}
