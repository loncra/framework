package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class AddAttachReq extends SignTaskBaseReq {
    private List<AddAttachInfo> attachs;

    public List<AddAttachInfo> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<AddAttachInfo> attachs) {
        this.attachs = attachs;
    }
}
