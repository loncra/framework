package io.github.loncra.framework.fasc.req.template;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @Author： zhupintian
 * @Date: 2023/7/17
 */
public class CreateDocTemplateReq extends BaseReq {

    private String openCorpId;

    private String docTemplateName;

    private String createSerialNo;

    private String fileId;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public String getDocTemplateName() {
        return docTemplateName;
    }

    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
