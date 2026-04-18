package io.github.loncra.framework.fasc.res.archives;

import java.util.List;

public class ArchivedDetailInfo {

    /**
     * 归档ID
     */
    private String archivesId;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 合同类型 e_contract：电子合同 paper_contract：纸质合同
     */
    private String contractType;

    /**
     * 文件夹ID
     */
    private String catalogId;

    /**
     * 关联签署任务ID，电子合同归档时存在
     */
    private String signTaskId;

    /**
     * 归档时间，格式为Unix标准时间戳（毫秒）
     */
    private String archiveTime;

    /**
     * 归档成员ID
     */
    private String memberId;

    /**
     * 归档存储类型
     */
    private String storageType;

    /**
     * 关联合同列表
     */
    private List<AssociatedContractsArchivesInfo> associatedContracts;

    /**
     * 关联附件列表
     */
    private List<AttachmentsArchivesInfo> attachments;

    /**
     * 关联合同归档信息
     */
    public static class AssociatedContractsArchivesInfo {

        /**
         * 归档ID
         */
        private String archivesId;

        /**
         * 合同名称
         */
        private String contractName;

        public String getArchivesId() {
            return archivesId;
        }

        public void setArchivesId(String archivesId) {
            this.archivesId = archivesId;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }
    }

    /**
     * 归档关联附件信息
     */
    public static class AttachmentsArchivesInfo {

        /**
         * 附件名称
         */
        private String attachmentName;

        public String getAttachmentName() {
            return attachmentName;
        }

        public void setAttachmentName(String attachmentName) {
            this.attachmentName = attachmentName;
        }
    }

    public String getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(String archivesId) {
        this.archivesId = archivesId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getSignTaskId() {
        return signTaskId;
    }

    public void setSignTaskId(String signTaskId) {
        this.signTaskId = signTaskId;
    }

    public String getArchiveTime() {
        return archiveTime;
    }

    public void setArchiveTime(String archiveTime) {
        this.archiveTime = archiveTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public List<AssociatedContractsArchivesInfo> getAssociatedContracts() {
        return associatedContracts;
    }

    public void setAssociatedContracts(List<AssociatedContractsArchivesInfo> associatedContracts) {
        this.associatedContracts = associatedContracts;
    }

    public List<AttachmentsArchivesInfo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentsArchivesInfo> attachments) {
        this.attachments = attachments;
    }
}
