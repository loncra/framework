package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:35:26
 */
public class CreateWithTemplateSignActorInfo extends BaseBean {
    private Actor signActor;
    private Boolean blockHere;
    private Boolean requestVerifyFree;
    private List<String> verifyMethods;

    public Actor getSignActor() {
        return signActor;
    }

    public void setSignActor(Actor signActor) {
        this.signActor = signActor;
    }

    public Boolean getBlockHere() {
        return blockHere;
    }

    public void setBlockHere(Boolean blockHere) {
        this.blockHere = blockHere;
    }

    public List<String> getVerifyMethods() {
        return verifyMethods;
    }

    public void setVerifyMethods(List<String> verifyMethods) {
        this.verifyMethods = verifyMethods;
    }

    public Boolean getRequestVerifyFree() {
        return requestVerifyFree;
    }

    public void setRequestVerifyFree(Boolean requestVerifyFree) {
        this.requestVerifyFree = requestVerifyFree;
    }
}
