package io.github.loncra.framework.fasc.res.corp;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.CorpIdentInfo;
import io.github.loncra.framework.fasc.bean.common.CorpInfoExtend;

/**
 * @author Fadada
 * 2021/10/16 17:50:32
 */
public class CorpIdentityInfoRes extends BaseBean {
    private String openCorpId;
    private String corpIdentStatus;
    private CorpIdentInfo corpIdentInfo;
    private CorpInfoExtend corpIdentInfoExtend;
    private String corpIdentMethod;
    private String operatorType;
    private String operatorId;
    private String operatorIdentMethod;
    private String identSubmitTime;
    private String identSuccessTime;
    private String fddId;
    private Boolean licenseCorpName;

    public Boolean getLicenseCorpName() {
        return licenseCorpName;
    }

    public void setLicenseCorpName(Boolean licenseCorpName) {
        this.licenseCorpName = licenseCorpName;
    }

    public String getFddId() {
        return fddId;
    }

    public void setFddId(String fddId) {
        this.fddId = fddId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getCorpIdentStatus() {
        return corpIdentStatus;
    }

    public void setCorpIdentStatus(String corpIdentStatus) {
        this.corpIdentStatus = corpIdentStatus;
    }

    public CorpIdentInfo getCorpIdentInfo() {
        return corpIdentInfo;
    }

    public void setCorpIdentInfo(CorpIdentInfo corpIdentInfo) {
        this.corpIdentInfo = corpIdentInfo;
    }

    public CorpInfoExtend getCorpIdentInfoExtend() {
        return corpIdentInfoExtend;
    }

    public void setCorpIdentInfoExtend(CorpInfoExtend corpIdentInfoExtend) {
        this.corpIdentInfoExtend = corpIdentInfoExtend;
    }

    public String getCorpIdentMethod() {
        return corpIdentMethod;
    }

    public void setCorpIdentMethod(String corpIdentMethod) {
        this.corpIdentMethod = corpIdentMethod;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorIdentMethod() {
        return operatorIdentMethod;
    }

    public void setOperatorIdentMethod(String operatorIdentMethod) {
        this.operatorIdentMethod = operatorIdentMethod;
    }

    public String getIdentSubmitTime() {
        return identSubmitTime;
    }

    public void setIdentSubmitTime(String identSubmitTime) {
        this.identSubmitTime = identSubmitTime;
    }

    public String getIdentSuccessTime() {
        return identSuccessTime;
    }

    public void setIdentSuccessTime(String identSuccessTime) {
        this.identSuccessTime = identSuccessTime;
    }
}
