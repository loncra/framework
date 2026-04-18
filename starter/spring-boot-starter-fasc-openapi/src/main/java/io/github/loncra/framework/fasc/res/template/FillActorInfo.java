package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * 2021/10/18 11:32:22
 */
public class FillActorInfo extends BaseBean {
    private String actorId;
    private Integer orderNo;
    private String actorIdentType;
    private List<FillActorFieldInfo> fillActorFields;

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getActorIdentType() {
        return actorIdentType;
    }

    public void setActorIdentType(String actorIdentType) {
        this.actorIdentType = actorIdentType;
    }

    public List<FillActorFieldInfo> getFillActorFields() {
        return fillActorFields;
    }

    public void setFillActorFields(List<FillActorFieldInfo> fillActorFields) {
        this.fillActorFields = fillActorFields;
    }
}