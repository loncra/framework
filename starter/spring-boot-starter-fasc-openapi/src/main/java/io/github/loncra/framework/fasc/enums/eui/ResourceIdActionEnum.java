package io.github.loncra.framework.fasc.enums.eui;

/**
 * @author Fadada
 * @date 2021/12/18 13:52:18
 */
public enum ResourceIdActionEnum {

    EDIT("EDIT", "编辑"),
    PREVIEW("PREVIEW", "预览"),
    SIGN("SIGN", "填/签"),
    MANAGE("MANAGE", "管理"),
    CREATE("CREATE", "新增"),
    ;

    private String code;
    private String remark;

    ResourceIdActionEnum(
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
