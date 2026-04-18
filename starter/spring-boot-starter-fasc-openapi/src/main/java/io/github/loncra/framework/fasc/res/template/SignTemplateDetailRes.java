package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.req.signtask.Watermark;
import io.github.loncra.framework.fasc.req.template.ApprovalInfo;

import java.util.List;

/**
 * @author Fadada
 * 2021/9/11 15:16:27
 */
public class SignTemplateDetailRes extends BaseBean {
    private String signTemplateId;
    private String signTemplateName;
    private String businessTypeName;
    private String signTemplateStatus;
    private String certCAOrg;
    private Boolean signInOrder;
    private List<DocumentInfo> docs;
    private List<AttachInfo> attachs;
    private List<SignTaskActorInfo> actors;
    private String catalogName;
    private String createSerialNo;
    private String storageType;
    private List<Watermark> watermarks;
    private List<ApprovalInfo> approvalInfos;

    public List<ApprovalInfo> getApprovalInfos() {
        return approvalInfos;
    }

    public void setApprovalInfos(List<ApprovalInfo> approvalInfos) {
        this.approvalInfos = approvalInfos;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getSignTemplateId() {
        return signTemplateId;
    }

    public void setSignTemplateId(String signTemplateId) {
        this.signTemplateId = signTemplateId;
    }

    public String getSignTemplateName() {
        return signTemplateName;
    }

    public void setSignTemplateName(String signTemplateName) {
        this.signTemplateName = signTemplateName;
    }

    public String getSignTemplateStatus() {
        return signTemplateStatus;
    }

    public void setSignTemplateStatus(String signTemplateStatus) {
        this.signTemplateStatus = signTemplateStatus;
    }

    public List<DocumentInfo> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentInfo> docs) {
        this.docs = docs;
    }

    public Boolean getSignInOrder() {
        return signInOrder;
    }

    public void setSignInOrder(Boolean signInOrder) {
        this.signInOrder = signInOrder;
    }

    public List<SignTaskActorInfo> getActors() {
        return actors;
    }

    public void setActors(List<SignTaskActorInfo> actors) {
        this.actors = actors;
    }

    public List<AttachInfo> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<AttachInfo> attachs) {
        this.attachs = attachs;
    }

    public String getCertCAOrg() {
        return certCAOrg;
    }

    public void setCertCAOrg(String certCAOrg) {
        this.certCAOrg = certCAOrg;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public List<Watermark> getWatermarks() {
        return watermarks;
    }

    public void setWatermarks(List<Watermark> watermarks) {
        this.watermarks = watermarks;
    }
}

