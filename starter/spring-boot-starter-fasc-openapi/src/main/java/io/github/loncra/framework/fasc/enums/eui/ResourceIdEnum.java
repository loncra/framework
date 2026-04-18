package io.github.loncra.framework.fasc.enums.eui;

/**
 * @author Fadada
 * @date 2021/12/18 13:52:18
 */
public enum ResourceIdEnum {

    SEAL("SEAL", "印章"),
    SIGN_TASK("SIGNTASK", "签署任务"),
    APP_TEMPLATE("APPTEMPLATE", "应用模板"),
    TEMPLATE("TEMPLATE", "模板"),
    ORGANIZATION("ORGANIZATION", "组织");

    private String code;
    private String remark;

    ResourceIdEnum(
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
