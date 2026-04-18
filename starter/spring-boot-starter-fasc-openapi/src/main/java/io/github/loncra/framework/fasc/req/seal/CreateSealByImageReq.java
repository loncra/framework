package io.github.loncra.framework.fasc.req.seal;

/**
 * @Author： zpt
 * @Date: 2023/7/21
 */
public class CreateSealByImageReq extends CreateSealByImageBaseInfo {

    private String openCorpId;

    /**
     * 印章主体id
     */
    private String entityId;

    /**
     * 印章名称，示例值：“深圳项目人事章”
     */
    private String sealName;

    /**
     * 企业印章类型
     */
    private String categoryType;

    /**
     * 印章标签，可以和业务系统的印章业务类型或者印章的子分类对应
     */
    private String sealTag;

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

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getSealTag() {
        return sealTag;
    }

    public void setSealTag(String sealTag) {
        this.sealTag = sealTag;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}