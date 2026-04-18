package io.github.loncra.framework.citic.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;


public enum RegionTypeEnum implements NameValueEnum<String> {

    MAINLAND_CHINA("156", "中国大陆"),
    TAIWAN_CHINA("158", "中国台湾"),
    HONG_KONG_CHINA("344", "中国香港"),
    MACAU_CHINA("446", "中国澳门"),
    OTHER_REGIONS("999", "其他");;

    RegionTypeEnum(
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
