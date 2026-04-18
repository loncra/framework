package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * @date 2021/12/6 11:30:13
 */
public class AddFieldReq extends SignTaskBaseReq {
    private List<AddFieldInfo> fields;
    private String actorId;

    public List<AddFieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<AddFieldInfo> fields) {
        this.fields = fields;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }
}
