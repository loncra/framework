package io.github.loncra.framework.fasc.req.signtask;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

import java.util.List;

public class SignTaskApplyReportReq extends BaseReq {

    private String signTaskId;
    private List<String> fileId;
    private OpenId ownerId;
    private String reportType;

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public List<String> getFileId() {
        return fileId;
    }

    public void setFileId(List<String> fileId) {
        this.fileId = fileId;
    }

    public OpenId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OpenId ownerId) {
        this.ownerId = ownerId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
