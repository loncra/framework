package io.github.loncra.framework.fasc.req.archives;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class ArchivesDetailReq extends BaseReq {

    private String openCorpId;

    private String archivesId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }
}
