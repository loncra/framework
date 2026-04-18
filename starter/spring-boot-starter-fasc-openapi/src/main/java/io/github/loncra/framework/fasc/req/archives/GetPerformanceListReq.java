package io.github.loncra.framework.fasc.req.archives;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetPerformanceListReq extends BaseReq {
    private String openCorpId;

    private String memberId;

    private String archivesId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }
}
