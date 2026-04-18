package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class AddDocReq extends SignTaskBaseReq {
    private List<AddDocInfo> docs;

    public List<AddDocInfo> getDocs() {
        return docs;
    }

    public void setDocs(List<AddDocInfo> docs) {
        this.docs = docs;
    }
}
