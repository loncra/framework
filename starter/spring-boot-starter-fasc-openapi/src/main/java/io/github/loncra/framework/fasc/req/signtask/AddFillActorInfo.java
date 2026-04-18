package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:35:16
 */
public class AddFillActorInfo extends BaseBean {
    private Actor fillActor;
    private Integer orderNo;
    private List<AddFillActorFieldInfo> actorFields;

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

    public List<AddFillActorFieldInfo> getActorFields() {
        return actorFields;
    }

    public void setActorFields(List<AddFillActorFieldInfo> actorFields) {
        this.actorFields = actorFields;
    }
}
