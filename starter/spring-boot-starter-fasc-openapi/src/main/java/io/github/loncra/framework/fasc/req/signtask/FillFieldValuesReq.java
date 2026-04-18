package io.github.loncra.framework.fasc.req.signtask;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 16:03:06
 */
public class FillFieldValuesReq extends SignTaskBaseReq {
    private List<DocFieldValueInfo> docFieldValues;

    public List<DocFieldValueInfo> getDocFieldValues() {
        return docFieldValues;
    }

    public void setDocFieldValues(List<DocFieldValueInfo> docFieldValues) {
        this.docFieldValues = docFieldValues;
    }
}
