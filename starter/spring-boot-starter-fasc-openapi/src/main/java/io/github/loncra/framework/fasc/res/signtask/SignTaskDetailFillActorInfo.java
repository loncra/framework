package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

/**
 * @author Fadada
 * 2021/9/13 16:53:59
 */
public class SignTaskDetailFillActorInfo extends BaseBean {
    private Actor fillActor;
    private Integer orderNo;
    private String fillActorStatus;
    private String actionTime;

    public Actor getFillActor() {
        return fillActor;
    }

    public void setFillActor(Actor fillActor) {
        this.fillActor = fillActor;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getFillActorStatus() {
        return fillActorStatus;
    }

    public void setFillActorStatus(String fillActorStatus) {
        this.fillActorStatus = fillActorStatus;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }
}
