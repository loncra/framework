package io.github.loncra.framework.fasc.res.org;

/**
 * @author zhoufucheng
 * @date 2023/9/6 11:50
 */
public class GetEntityListRes {
    private String entityId;
    private String entityType;  //primary ; subsidiary
    private String corpName;
    private String corpIdentNo;
    private String legalRepName;
    private String identStatus;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

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

    public String getIdentStatus() {
        return identStatus;
    }

    public void setIdentStatus(String identStatus) {
        this.identStatus = identStatus;
    }
}
