package io.github.loncra.framework.fasc.enums.org;

/**
 * @author zhoufucheng
 * @date 2022/12/5 17:17
 */
public enum SetMemberDeptsModeEnum {
    COVER("cover", "覆盖"),
    APPEND("append", "追加"),
    ;

    private String code;
    private String remark;

    SetMemberDeptsModeEnum(
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
