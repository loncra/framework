package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * @date 2021/12/6 11:30:13
 */
public class DeleteFieldReq extends SignTaskBaseReq {
    private List<DeleteFieldInfo> fields;

    public List<DeleteFieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<DeleteFieldInfo> fields) {
        this.fields = fields;
    }
}
