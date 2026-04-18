package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class SignTaskDownloadReportReq extends BaseReq {

    private String reportDownloadId;

    public String getReportDownloadId() {
        return reportDownloadId;
    }

    public void setReportDownloadId(String reportDownloadId) {
        this.reportDownloadId = reportDownloadId;
    }
}
