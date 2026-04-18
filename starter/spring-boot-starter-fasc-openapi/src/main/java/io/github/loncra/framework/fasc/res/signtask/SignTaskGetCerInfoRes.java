package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

public class SignTaskGetCerInfoRes extends BaseBean {
    private List<CerInfoActor> actors;

    public List<CerInfoActor> getActors() {
        return actors;
    }

    public void setActors(List<CerInfoActor> actors) {
        this.actors = actors;
    }
}
