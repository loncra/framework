package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class BusinessFourElementVerifyReq extends BaseReq {
    private String corpName;
    private String corpIdentNo;
    private String legalRepName;
    private String legalRepIdCertNo;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpIdentNo() {
        return corpIdentNo;
    }

    public void setCorpIdentNo(String corpIdentNo) {
        this.corpIdentNo = corpIdentNo;
    }

    public String getLegalRepName() {
        return legalRepName;
    }

    public void setLegalRepName(String legalRepName) {
        this.legalRepName = legalRepName;
    }

    public String getLegalRepIdCertNo() {
        return legalRepIdCertNo;
    }

    public void setLegalRepIdCertNo(String legalRepIdCertNo) {
        this.legalRepIdCertNo = legalRepIdCertNo;
    }
}
