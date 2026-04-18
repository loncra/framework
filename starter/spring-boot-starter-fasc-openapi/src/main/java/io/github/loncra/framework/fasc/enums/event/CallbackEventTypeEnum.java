package io.github.loncra.framework.fasc.enums.event;

public enum CallbackEventTypeEnum {

    SIGN_TASK_ACTOR_SIGNED("sign-task-signed", "签署任务签署方签署事件"),
    SIGN_TASK_SIGN_FAILED("sign-task-sign-failed", "签署任务参与方签署失败事件"),
    SIGN_TASK_ACTOR_SIGN_REFUSED("sign-task-sign-rejected", "签署任务签署方拒签事件"),
    SIGN_TASK_ACTOR_FILLED("sign-task-filled", "签署任务签署方填充事件"),
    SIGN_TASK_ACTOR_FILL_REFUSED("sign-task-fill-rejected", "签署任务签署方填充拒签事件"),
    SIGN_TASK_FINISHED("sign-task-finished", "签署任务完成事件"),
    SIGN_TASK_CANCELED("sign-task-canceled", "签署任务取消事件"),
    SIGN_TASK_EXPIRE("sign-task-expire", "签署任务过期事件"),
    SIGN_TASK_JOINED("sign-task-joined", "签署任务加入事件"),
    SIGN_TASK_START("sign-task-start", "签署任务提交事件"),
    SIGN_TASK_READ("sign-task-read", "签署任务阅读数事件"),
    SIGN_TASK_ABOLISH("sign-task-abolish", "签署任务作废事件"),
    SIGN_TASK_JOIN_FAILED("sign-task-join-failed", "签署任务加入失败事件"),
    SIGN_TASK_DOWNLOAD("sign-task-download", "签署文档批量下载事件"),
    SIGN_TASK_EXTENSION("sign-task-extension", "签署任务延期事件"),
    SIGN_TASK_REFUSE_FILL("sign-task-ignore", "签署任务驳回填写事件"),
    SIGN_TASK_FINALIZE_TASK("sign-task-finalize", "签署任务定稿事件"),

    SIGN_TASK_PENDING("sign-task-pending", "签署任务待处理事件（API3.0任务专属）"),

    //需要回调给所有参与方的事件
    // 关联企业
    ORGANIZATION_RELATION_CORP_CONNECTED("organization-relation-corp-connected", "关联企业联接建立事件"),
    ORGANIZATION_RELATION_CORP_REMOVE("organization-relation-corp-remove", "关联企业联接解除事件"),
    ORGANIZATION_RELATION_CORPMEMBER_ADD("organization-relation-corpmemeber-add", "关联企业添加外派成员事件"),
    ORGANIZATION_RELATION_CORPMEMBER_REMOVE("organization-relation-corpmemeber-remove", "关联企业移除外派成员事件"),
    ORGANIZATION_RELATION_INVITATION_ADD("organization-relation-invitation-add", "关联企业联接邀请事件"),
    ORGANIZATION_RELATION_INVITATION_CANCEL("organization-relation-invitation-cancel", "关联企业撤销联接邀请事件"),
    ORGANIZATION_RELATION_INVITATION_STATUS_CHANGE("organization-relation-invitation-status-change", "关联企业联接邀请状态变化事件"),

    //部门相关事件
    ORGANIZATION_CORP_DEPT_CREATE_EVENT("organization-dept-create", "企业部门创建事件"),
    ORGANIZATION_CORP_DEPT_MODIFY_EVENT("organization-dept-modify", "企业部门修改事件"),
    ORGANIZATION_CORP_DEPT_DELETE_EVENT("organization-dept-delete", "企业部门删除事件"),

    //企业成员相关事件
    ORGANIZATION_CORP_MEMBER_CREATE_EVENT("organization-member-create", "成员创建事件"),
    ORGANIZATION_CORP_MEMBER_ACTIVE_EVENT("organization-member-active", "成员激活事件"),
    ORGANIZATION_CORP_MEMBER_MODIFY_INFO_EVENT("organization-member-modify-info", "成员基本信息修改事件"),
    ORGANIZATION_CORP_MEMBER_MODIFY_DEPT_EVENT("organization-member-modify-dept", "成员部门修改事件"),
    ORGANIZATION_CORP_MEMBER_ENABLE_EVENT("organization-member-enable", "成员启用事件"),
    ORGANIZATION_CORP_MEMBER_DISABLE_EVENT("organization-member-disable", "成员禁用事件"),
    ORGANIZATION_CORP_MEMBER_DELETE_EVENT("organization-member-delete", "成员删除事件"),

    // 印章
    SEAL_CANCELLATION("seal-cancellation", "印章注销事件"),
    SEAL_CREATE("seal-create", "印章创建事件"),
    SEAL_VERIFY_SUCCESSED("seal-verify-successed", "印章审核通过事件"),
    SEAL_VERIFY_FAILED("seal-verify-failed", "印章审核不通过事件"),
    SEAL_VERIFY_CANCEL("seal-verify-cancel", "印章审核撤销事件"),
    SEAL_ENABLE("seal-enable", "印章启用事件"),
    SEAL_DISABLE("seal-disable", "印章停用事件"),
    SEAL_DELETE("seal-delete", "印章删除事件"),
    SEAL_MODIFY_INFO("seal-modify-info", "印章编辑事件"),
    SEAL_AUTHORIZE_MEMBER("seal-authorize-member", "印章授权成员事件"),
    SEAL_AUTHORIZE_MEMBER_CANCEL("seal-authorize-member-cancel", "印章授权解除事件"),
    SEAL_AUTHORIZE_FREE_SIGN("seal-authorize-free-sign", "印章授权免验证签事件"),
    SEAL_AUTHORIZE_FREE_SIGN_CANCEL("seal-authorize-free-sign-cancel", "印章免验证签解除事件"),
    SEAL_AUTHORIZE_FREE_SIGN_DUE_CANCEL("seal-authorize-free-sign-due-cancel", "印章设置免验证签即将到期事件"),
    PERSONAL_SEAL_AUTHORIZE_FREE_SIGN("personal-seal-authorize-free-sign", "个人签名授权免验证签事件"),
    PERSONAL_SEAL_AUTHORIZE_FREE_SIGN_CANCEL("personal-seal-authorize-free-sign-cancel", "个人签名解除免验证签授权事件"),
    PERSONAL_SEAL_AUTHORIZE_FREE_SIGN_DUE_CANCEL("personal-seal-authorize-free-sign-due-cancel", "签名设置免验证签即将到期事件"),
    PERSONAL_SEAL_CREATE("personal-seal-create", "个人签名创建事件"),
    PERSONAL_SEAL_DELETE("personal-seal-delete", "个人签名删除事件"),

    // 模板
    TEMPLATE_CREATE("template-create", "模板创建事件"),
    TEMPLATE_ENABLE("template-enable", "模板启用事件"),
    TEMPLATE_DISABLE("template-disable", "模板停用事件"),
    TEMPLATE_DELETE("template-delete", "模板删除事件"),

    // 合同
    OCR_EXAMINE_STATUS("ocr-examine-status", "合同智审结果事件"),
    OCR_COMPARE_STATUS("ocr-compare-status", "合同比对事件"),


    //用户解绑相关事件
    OPEN_CORP_UNBIND_EVENT("corp-cancel-authorization", "企业解除授权事件"),
    OPEN_USER_UNBIND_EVENT("user-cancel-authorization", "个人解除授权事件"),

    //三四要素相关事件
    THREE_ELEMENTS_VERIFY_EVENT("user-three-element-verify", "三要素校验事件"),
    FOUR_ELEMENTS_VERIFY_EVENT("user-four-element-verify", "四要素校验事件"),
    FACE_RECOGNIZE_EVENT("face-recognition", "人脸核验事件"),


    //审核
    APPROVAL_CREAT("approval-create", "审批发起事件"),
    APPROVAL_CHANGE("approval-change", "审批变更事件"),
    //填签
    SIGN_FINISH("voucher-sign-task-finished", "签署任务完成事件"),
    SIGN_SUCCEE("voucher-sign-task-signed", "参与方签署成功事件"),
    REJECT_SIGN("voucher-sign-task-sign-rejected", "参与方拒签事件"),
    TASK_DOWN_LOAD("voucher-sign-task-download", "单据任务下载"),

    ;


    private String value;
    private String valueInFact;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueInFact() {
        return valueInFact;
    }

    public void setValueInFact(String valueInFact) {
        this.valueInFact = valueInFact;
    }

    CallbackEventTypeEnum(
            String value,
            String valueInFact
    ) {
        this.value = value;
        this.valueInFact = valueInFact;
    }
}
