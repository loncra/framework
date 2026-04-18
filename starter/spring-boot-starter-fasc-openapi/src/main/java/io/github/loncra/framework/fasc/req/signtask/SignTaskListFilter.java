package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author gongj
 * @date 2022/7/12
 */
public class SignTaskListFilter extends BaseBean {
    private String signTaskSubject;
    private List<String> signTaskStatus;
    private String transReferenceId;

    public String getSignTaskSubject() {
        return signTaskSubject;
    }

    public void setSignTaskSubject(String signTaskSubject) {
        this.signTaskSubject = signTaskSubject;
    }

    public List<String> getSignTaskStatus() {
        return signTaskStatus;
    }

    public void setSignTaskStatus(List<String> signTaskStatus) {
        this.signTaskStatus = signTaskStatus;
    }

    public String getTransReferenceId() {
        return transReferenceId;
    }

    public void setTransReferenceId(String transReferenceId) {
        this.transReferenceId = transReferenceId;
    }

}
