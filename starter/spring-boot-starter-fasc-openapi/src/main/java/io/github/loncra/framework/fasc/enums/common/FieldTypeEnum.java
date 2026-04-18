package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * 2021/9/23 11:03:29
 */
public enum FieldTypeEnum {

    PERSON_SIGN("person_sign", "个人签名"),
    CORP_SEAL("corp_seal", "企业印章"),
    CORP_SEAL_CROSS_PAGE("corp_seal_cross_page", "企业骑缝章"),
    DATE_SIGN("date_sign", "日期戳"),

    TEXT_SINGLE_LINE("text_single_line", "单行文本"),
    TEXT_MULTI_LINE("text_multi_line", "多行文本"),
    NUMBER("number", "数字控件"),
    ID_CARD("id_card", "身份证号控件"),
    FILL_DATE("fill_date", "填写日期控件"),
    MULTI_RADIO("multi_radio", "单选框-多项控件"),
    MULTI_CHECKBOX("multi_checkbox", "复选框-多项控件"),
    CHECK_BOX("check_box", "复选框"),
    REMARK_SIGN("remark_sign", "备注区"),
    SELECT_BOX("select_box", "下拉选择项控件"),
    PICTURE("picture", "图片"),
    TABLE("table", "表格"),
    ;

    private String code;
    private String remark;

    FieldTypeEnum(
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
