package io.github.loncra.framework.fasc.enums.corp;

/**
 * @author Fadada
 * 2021/10/23 16:13:29
 */
public enum CorpAuthScopeEnum {

    IDENT_INFO("ident_info", "实名信息"),
    SEAL_INFO("seal_info", "印章及用印员"),
    SIGN_TASK_INIT("signtask_init", "签署任务创建及发起"),
    SIGN_TASK_INFO("signtask_info", "签署任务"),
    SIGN_TASK_FILE("signtask_file", "签署文件"),
    TEMPLATE("template", "模板"),
    ORGANIZATION("organization", "组织"),
    ;

    private String code;
    private String remark;

    CorpAuthScopeEnum(
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
