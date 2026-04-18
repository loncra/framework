package io.github.loncra.framework.fasc.enums.user;

/**
 * @author Fadada
 * 2021/9/23 10:12:04
 */
public enum UserIdentTypeEnum {
    /**
     * 证件类型 ：
     * id_card: 身份证
     * passport: 护照
     * hk_macao: 港澳居民来往内地通行证
     * taiwan: 台湾居民来往大陆通行证
     */
    ID_CARD("id_card", "身份证"),
    PASSPORT("passport", "护照"),
    HK_MACAO("hk_macao", "港澳居民来往内地通行证"),
    TAIWAN("taiwan", "台湾居民来往大陆通行证");

    private String code;
    private String remark;

    UserIdentTypeEnum(
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
