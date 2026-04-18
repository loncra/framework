package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class DeleteDocReq extends SignTaskBaseReq {
    private List<String> docIds;

    public List<String> getDocIds() {
        return docIds;
    }

    public void setDocIds(List<String> docIds) {
        this.docIds = docIds;
    }
}
