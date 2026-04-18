package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class DeleteAttachReq extends SignTaskBaseReq {
    private List<String> attachIds;

    public List<String> getAttachIds() {
        return attachIds;
    }

    public void setAttachIds(List<String> attachIds) {
        this.attachIds = attachIds;
    }
}
