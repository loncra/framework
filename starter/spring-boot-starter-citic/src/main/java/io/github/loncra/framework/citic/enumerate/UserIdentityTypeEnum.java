package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;

public enum UserIdentityTypeEnum implements NameValueEnum<String> {

    PERSONAL_ID("01", "个人身份证"),
    HOUSEHOLD_REGISTER("22", "户口簿"),
    FOREIGN_PASSPORT("23", "外国护照"),
    ARMY_OFFICER("25", "军人军官证"),
    ARMY_SOLDIER("26", "军人士兵证"),
    POLICE_OFFICER("27", "武警军官证"),
    HK_RESIDENT_PERMIT("28", "港澳居民往来内地通行证（香港）"),
    TAIWAN_RESIDENT_PERMIT("29", "台湾居民往来大陆通行证"),
    TEMPORARY_ID_CARD("30", "临时居民身份证"),
    FOREIGNER_PERMIT("31", "外国人永久居留证"),
    CHINESE_PASSPORT("32", "中国护照"),
    POLICE_SOLDIER("33", "武警士兵证"),
    MACAU_RESIDENT_PERMIT("34", "港澳居民往来内地通行证（澳门）"),
    BORDER_RESIDENT_PERMIT("35", "边民出入境通行证"),
    TAIWAN_TRAVEL_PERMIT("36", "台湾居民旅行证"),
    HK_RESIDENT_ID("37", "港澳居民居住证（香港）"),
    MACAU_RESIDENT_ID("38", "港澳居民居住证（澳门）"),
    TAIWAN_RESIDENT_ID("39", "台湾居民居住证"),

    ;

    UserIdentityTypeEnum(
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
