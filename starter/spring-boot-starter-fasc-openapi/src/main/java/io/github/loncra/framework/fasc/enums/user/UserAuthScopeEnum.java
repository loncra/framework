package io.github.loncra.framework.fasc.enums.user;

/**
 * @author Fadada
 * 2021/10/23 16:13:29
 */
public enum UserAuthScopeEnum {

    IDENT_INFO("ident_info", "实名信息"),
    SIGN_TASK_INIT("signtask_init", "签署任务创建及发起"),
    SIGN_TASK_INFO("signtask_info", "签署任务"),
    SIGN_TASK_FILE("signtask_file", "签署文件"),
    SEAL_INFO("seal_info", "个人签名"),
    ;

    private String code;
    private String remark;

    UserAuthScopeEnum(
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
