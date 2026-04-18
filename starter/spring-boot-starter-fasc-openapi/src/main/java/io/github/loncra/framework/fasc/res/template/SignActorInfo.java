package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * 2021/10/18 11:31:21
 */
public class SignActorInfo extends BaseBean {
    private String actorId;
    private Integer orderNo;
    private String actorIdentType;
    private Boolean corpOperatorSign;
    private String signerSignMethod;
    private List<SignActorFieldInfo> signActorFields;

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

    public Boolean getCorpOperatorSign() {
        return corpOperatorSign;
    }

    public void setCorpOperatorSign(Boolean corpOperatorSign) {
        this.corpOperatorSign = corpOperatorSign;
    }

    public String getSignerSignMethod() {
        return signerSignMethod;
    }

    public void setSignerSignMethod(String signerSignMethod) {
        this.signerSignMethod = signerSignMethod;
    }

    public List<SignActorFieldInfo> getSignActorFields() {
        return signActorFields;
    }

    public void setSignActorFields(List<SignActorFieldInfo> signActorFields) {
        this.signActorFields = signActorFields;
    }
}