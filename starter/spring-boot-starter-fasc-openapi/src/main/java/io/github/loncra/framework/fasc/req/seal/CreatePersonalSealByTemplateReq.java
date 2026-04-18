package io.github.loncra.framework.fasc.req.seal;


/**
 * @Author： zpt
 * @Date: 2023/7/21
 */
public class CreatePersonalSealByTemplateReq extends CreateSealByTemplateBaseInfo {

    private String openUserId;

    /**
     * 签名名称
     */
    private String sealName;

    /**
     * 业务系统定义的印章创建序列号：用于在印章创建后的回调事件中将sealId和createSerialNo进行对应
     */
    private String createSerialNo;

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

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}