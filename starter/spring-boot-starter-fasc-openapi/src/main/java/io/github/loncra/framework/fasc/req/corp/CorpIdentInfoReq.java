package io.github.loncra.framework.fasc.req.corp;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

public class CorpIdentInfoReq extends BaseBean {
    private String corpName;
    private String corpIdentType;
    private String corpIdentNo;
    private String legalRepName;
    private String license;
    private List<String> corpIdentMethod;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpIdentType() {
        return corpIdentType;
    }

    public void setCorpIdentType(String corpIdentType) {
        this.corpIdentType = corpIdentType;
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<String> getCorpIdentMethod() {
        return corpIdentMethod;
    }

    public void setCorpIdentMethod(List<String> corpIdentMethod) {
        this.corpIdentMethod = corpIdentMethod;
    }
}
