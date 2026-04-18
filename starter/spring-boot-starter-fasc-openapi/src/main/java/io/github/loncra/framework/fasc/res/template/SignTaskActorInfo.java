package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

public class SignTaskActorInfo extends BaseBean {
    private ActorInfo actorInfo;

    private List<FillActorFieldInfo> fillFields;

    private List<SignActorFieldInfo> signFields;

    private SignConfigInfo signConfigInfo;

    private NotificationRes notification;

    public ActorInfo getActorInfo() {
        return actorInfo;
    }

    public void setActorInfo(ActorInfo actorInfo) {
        this.actorInfo = actorInfo;
    }

    public List<FillActorFieldInfo> getFillFields() {
        return fillFields;
    }

    public void setFillFields(List<FillActorFieldInfo> fillFields) {
        this.fillFields = fillFields;
    }

    public List<SignActorFieldInfo> getSignFields() {
        return signFields;
    }

    public void setSignFields(List<SignActorFieldInfo> signFields) {
        this.signFields = signFields;
    }

    public SignConfigInfo getSignConfigInfo() {
        return signConfigInfo;
    }

    public void setSignConfigInfo(SignConfigInfo signConfigInfo) {
        this.signConfigInfo = signConfigInfo;
    }

    public NotificationRes getNotification() {
        return notification;
    }

    public void setNotification(NotificationRes notification) {
        this.notification = notification;
    }

    public static class NotificationRes {

        private String notifyWay;

        private String notifyAddress;

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
    }
}