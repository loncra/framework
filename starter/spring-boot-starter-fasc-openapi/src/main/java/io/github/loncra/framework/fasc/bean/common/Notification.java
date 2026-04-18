package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 14:35:51
 */
public class Notification extends BaseBean {
    private Boolean sendNotification;
    private String notifyWay;
    private String notifyAddress;
    private Boolean appointAccount;
    private CustomNotifyContent customNotifyContent;

    public Boolean getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(Boolean sendNotification) {
        this.sendNotification = sendNotification;
    }

    public String getNotifyWay() {
        return notifyWay;
    }

    public void setNotifyWay(String notifyWay) {
        this.notifyWay = notifyWay;
    }

    public String getNotifyAddress() {
        return notifyAddress;
    }

    public void setNotifyAddress(String notifyAddress) {
        this.notifyAddress = notifyAddress;
    }

    public CustomNotifyContent getCustomNotifyContent() {
        return customNotifyContent;
    }

    public void setCustomNotifyContent(CustomNotifyContent customNotifyContent) {
        this.customNotifyContent = customNotifyContent;
    }

    public static Notification getInstance(
            boolean sendNotification,
            String notifyWay,
            String notifyAddress
    ) {
        return new Notification(sendNotification, notifyWay, notifyAddress);
    }

    public Notification(
            Boolean sendNotification,
            String notifyWay,
            String notifyAddress
    ) {
        this.sendNotification = sendNotification;
        this.notifyWay = notifyWay;
        this.notifyAddress = notifyAddress;
    }

    public Notification() {
    }

    public Boolean getAppointAccount() {
        return appointAccount;
    }

    public void setAppointAccount(Boolean appointAccount) {
        this.appointAccount = appointAccount;
    }
}
