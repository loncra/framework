package io.github.loncra.framework.fasc.enums.event;

import java.util.Objects;

/**
 * @author Fadada
 * @deprecated 使用 {@link CallbackEventTypeEnum} 替换此类
 * 2021/10/21 15:37:11
 */
@Deprecated
public enum CallbackEventEnum {
    /**
     * 回调事件枚举
     */
    USER_AUTHORIZE("user-authorize", "用户授权事件"),
    CORP_AUTHORIZE("corp-authorize", "企业授权事件"),
    SIGN_TASK_JOINED("sign-task-joined", "签署任务加入事件"),
    SIGN_TASK_FILLED("sign-task-filled", "签署任务填写事件"),
    SIGN_TASK_FILL_REJECTED("sign-task-fill-rejected", "签署任务拒填事件"),
    SIGN_TASK_SIGNED("sign-task-signed", "签署任务签署事件"),
    SIGN_TASK_SIGN_FAILED("sign-task-sign-failed", "签署任务签署失败事件"),
    SIGN_TASK_SIGN_REJECTED("sign-task-sign-rejected", "签署任务拒签事件"),
    SIGN_TASK_FINISHED("sign-task-finished", "签署任务完成事件"),
    SIGN_TASK_CANCELED("sign-task-canceled", "签署任务撤销事件"),
    SIGN_TASK_EXPIRE("sign-task-expire", "签署任务过期事件");

    private String eventCode;
    private String eventName;

    CallbackEventEnum(
            String eventCode,
            String eventName
    ) {
        this.eventCode = eventCode;
        this.eventName = eventName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    /**
     * 根据事件编码返回指定枚举
     *
     * @param eventCode 事件编码
     *
     * @return 事件枚举
     */
    public static CallbackEventEnum getCallbackEventEnum(String eventCode) {
        for (CallbackEventEnum callbackEventEnum : CallbackEventEnum.values()) {
            if (Objects.equals(callbackEventEnum.eventCode, eventCode)) {
                return callbackEventEnum;
            }
        }
        return null;
    }

}
