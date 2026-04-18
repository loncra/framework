package io.github.loncra.framework.fasc.req.seal;


/**
 * @Author： zpt
 * @Date: 2023/7/21
 */
public class CreateLegalRepresentativeSealByTemplateReq extends CreateSealByTemplateBaseInfo {

    private String openCorpId;


    /**
     * 印章主体id
     */
    private String entityId;

    private String openUserId;

    /**
     * 签名名称
     */
    private String sealName;

    /**
     * 印章标签，可以和业务系统的印章业务类型或者印章的子分类对应
     */
    private String sealTag;

    /**
     * 防伪码，人名下面增加一行数字编码，例如企业实体印章的伪码，仅支持数字、字母、英文符号。
     */
    private String securityCode;

    /**
     * 业务系统定义的印章创建序列号：用于在印章创建后的回调事件中将sealId和createSerialNo进行对应
     */
    private String createSerialNo;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getSealTag() {
        return sealTag;
    }

    public void setSealTag(String sealTag) {
        this.sealTag = sealTag;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}