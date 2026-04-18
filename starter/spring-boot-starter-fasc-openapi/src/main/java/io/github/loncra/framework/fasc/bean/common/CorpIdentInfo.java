package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * 2021/9/11 14:32:05
 */
public class CorpIdentInfo extends BaseBean {
    private String corpName;
    private String corpIdentType;
    private String corpIdentNo;
    private String legalRepName;

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
}
