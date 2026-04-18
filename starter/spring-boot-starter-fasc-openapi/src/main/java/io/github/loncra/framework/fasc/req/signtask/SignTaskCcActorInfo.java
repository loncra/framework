package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Actor;

/**
 * @author Fadada
 * 2021/10/18 14:45:32
 */
public class SignTaskCcActorInfo extends BaseBean {
    private Actor ccActor;

    public Actor getCcActor() {
        return ccActor;
    }

    public void setCcActor(Actor ccActor) {
        this.ccActor = ccActor;
    }
}
