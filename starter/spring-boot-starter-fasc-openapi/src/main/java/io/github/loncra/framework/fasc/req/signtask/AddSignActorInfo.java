package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:35:26
 */
public class AddSignActorInfo extends BaseBean {
    private Actor signActor;
    private Integer orderNo;
    private Boolean blockHere;
    private Boolean requestVerifyFree;
    private List<String> verifyMethods;
    private Boolean corpOperatorSign;
    private String signerSignMethod;
    private List<AddSignActorFieldInfo> actorFields;

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

    public Boolean getBlockHere() {
        return blockHere;
    }

    public void setBlockHere(Boolean blockHere) {
        this.blockHere = blockHere;
    }

    public Boolean getRequestVerifyFree() {
        return requestVerifyFree;
    }

    public void setRequestVerifyFree(Boolean requestVerifyFree) {
        this.requestVerifyFree = requestVerifyFree;
    }

    public List<String> getVerifyMethods() {
        return verifyMethods;
    }

    public void setVerifyMethods(List<String> verifyMethods) {
        this.verifyMethods = verifyMethods;
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

    public List<AddSignActorFieldInfo> getActorFields() {
        return actorFields;
    }

    public void setActorFields(List<AddSignActorFieldInfo> actorFields) {
        this.actorFields = actorFields;
    }
}
