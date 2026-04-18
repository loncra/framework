package io.github.loncra.framework.fasc.res.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author hukc
 * @date 2022年10月31日
 */
public class ListSignTaskFieldRes extends BaseBean {
    private Long signTaskId;
    private String signTaskSubject;
    private List<ListSignTaskFillField> fillFields;

    public Long getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(Long signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getSignTaskSubject() {
        return signTaskSubject;
    }

    public void setSignTaskSubject(String signTaskSubject) {
        this.signTaskSubject = signTaskSubject;
    }

    public List<ListSignTaskFillField> getFillFields() {
        return fillFields;
    }

    public void setFillFields(List<ListSignTaskFillField> fillFields) {
        this.fillFields = fillFields;
    }
}
