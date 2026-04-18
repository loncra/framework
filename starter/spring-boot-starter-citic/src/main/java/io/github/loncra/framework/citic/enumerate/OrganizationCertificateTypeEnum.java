package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum OrganizationCertificateTypeEnum implements NameValueEnum<String> {

    ORGANIZATION_CODE("02", "组织机构代码"),
    UNIFIED_SOCIAL_CREDIT_CODE("03", "统一社会信用代码"),
    PRIVATE_NON_ENTERPRISE("04", "民办非企业登记证书"),
    SOCIAL_GROUP_LEGAL_PERSON("05", "社会团体法人登记证书"),
    PUBLIC_INSTITUTION_LEGAL_PERSON("06", "事业单位法人登记证"),
    BUSINESS_LICENSE("07", "营业执照号码"),
    OTHER_ORGANIZATION_CERTIFICATE("08", "其他单位证件"),

    ;

    OrganizationCertificateTypeEnum(
            String value,
            String name
    ) {
        this.name = name;
        this.value = value;
    }

    private final String value;

    private final String name;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

}
