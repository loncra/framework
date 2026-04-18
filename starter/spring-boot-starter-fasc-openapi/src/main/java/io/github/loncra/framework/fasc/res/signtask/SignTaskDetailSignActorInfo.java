package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

/**
 * @author Fadada
 * 2021/9/13 16:53:01
 */
public class SignTaskDetailSignActorInfo extends BaseBean {
    private Actor signActor;
    private Integer orderNo;
    private String signActorStatus;
    private String actionTime;

    public Actor getSignActor() {
        return signActor;
    }

    public void setSignActor(Actor signActor) {
        this.signActor = signActor;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getSignActorStatus() {
        return signActorStatus;
    }

    public void setSignActorStatus(String signActorStatus) {
        this.signActorStatus = signActorStatus;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }
}
