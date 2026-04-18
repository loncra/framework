package io.github.loncra.framework.fasc.enums.doc;

/**
 * @author Fadada
 * @date 2021/12/16 10:22:33
 */
public enum FileTypeEnum {

    DOC("doc", "文档"),
    ATTACH("attach", "附件"),
    ;

    private String code;
    private String remark;

    FileTypeEnum(
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
