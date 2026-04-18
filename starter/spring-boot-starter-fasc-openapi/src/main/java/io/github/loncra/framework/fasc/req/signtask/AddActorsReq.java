package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class AddActorsReq extends SignTaskBaseReq {

    private List<AddActorsInfo> actors;

    public List<AddActorsInfo> getActors() {
        return actors;
    }

    public void setActors(List<AddActorsInfo> actors) {
        this.actors = actors;
    }
}
