package io.github.loncra.framework.fasc.req.voucher;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class VoucherCancelReq extends BaseReq {

    private String signTaskId;

    private String terminationNote;

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getTerminationNote() {
        return terminationNote;
    }

    public void setTerminationNote(String terminationNote) {
        this.terminationNote = terminationNote;
    }
}