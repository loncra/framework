package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;


public enum OccupationTypeEnum implements NameValueEnum<String> {

    CIVIL_SERVANT("00", "公务员"),
    SERVICE_INDUSTRY("05", "服务业从业人员"),
    AGRICULTURAL_WORKER("06", "农、林、牧、渔业生产人员"),
    WORKER("07", "工人"),
    HOUSEWIFE_HOUSEHUSBAND("12", "家庭主妇/主夫"),
    TEACHER("13", "教师"),
    LAWYER("14", "律师"),
    MEDICAL_STAFF("15", "医务人员"),
    STUDENT("17", "学生"),
    RETIREE("18", "离退休人员"),
    PRIVATE_BUSINESS_OWNER("19", "私营业主"),
    FREELANCER("20", "自由职业者"),
    UNEMPLOYED("21", "无业人员"),
    NON_PROFIT_ORGANIZATION("24", "群众团体、社会团队和其他成员组织人员"),
    PUBLIC_INSTITUTION_EMPLOYEE("25", "事业单位员工"),
    COMPANY_EMPLOYEE("26", "公司员工"),
    FOREIGN_DIGNITARY("28", "外国政要、国际组织高级管理人员及其关系人"),
    COMPANY_EXECUTIVE("30", "公司负责人和高管"),
    PUBLIC_INSTITUTION_LEADER("31", "事业单位负责人"),
    FINANCE_PROFESSIONAL("32", "金融、财务从业者"),
    IT_TECHNICIAN("33", "IT技术人员"),
    MILITARY_POLICE("34", "军人、武警"),
    RESEARCHER("35", "科研人员"),
    PRESCHOOL_CHILD("36", "学龄前儿童"),
    ARTS_SPORTS_PROFESSIONAL("37", "文学艺术、体育专业人员"),
    OTHER_PROFESSIONAL("38", "其他专业技术人员"),
    ONLINE_BUSINESS_OPERATOR("39", "网商经营者"),

    ;

    OccupationTypeEnum(
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
