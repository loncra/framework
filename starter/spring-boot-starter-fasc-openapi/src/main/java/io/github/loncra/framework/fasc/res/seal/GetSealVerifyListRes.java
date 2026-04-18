package io.github.loncra.framework.fasc.res.seal;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class GetSealVerifyListRes extends BaseBean {

    private List<VerifyInfo> verifyInfos;

    public List<VerifyInfo> getVerifyInfos() {
        return verifyInfos;
    }

    public void setVerifyInfos(List<VerifyInfo> verifyInfos) {
        this.verifyInfos = verifyInfos;
    }

    public static class VerifyInfo {
        /**
         * 印章审核工单ID
         */
        private Long verifyId;

        /**
         * 印章名称
         */
        private String sealName;

        /**
         * 印章类型：official_seal-法定名称章（公章）, contract_seal-合同专用章, hr_seal-人事专用章, financial_seal-财务专用章, legal_representative_seal-法定代表人名章, other-其他
         */
        private String categoryType;

        /**
         * 印章章模图片文件
         */
        private String picFileId;

        /**
         * 印章图片地址
         */
        private String picFileUrl;

        /**
         * 印章创建时间
         */
        private String createTime;

        /**
         * 印章审核状态：verifing-审核中，verify_failed-审核不通过
         */
        private String verifyStatus;

        /**
         * 印章审核不通过原因，审核状态是不通过时返回
         */
        private String rejectReasons;

        public Long getVerifyId() {
            return verifyId;
        }

        public void setVerifyId(Long verifyId) {
            this.verifyId = verifyId;
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

        public String getPicFileId() {
            return picFileId;
        }

        public void setPicFileId(String picFileId) {
            this.picFileId = picFileId;
        }

        public String getPicFileUrl() {
            return picFileUrl;
        }

        public void setPicFileUrl(String picFileUrl) {
            this.picFileUrl = picFileUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(String verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public String getRejectReasons() {
            return rejectReasons;
        }

        public void setRejectReasons(String rejectReasons) {
            this.rejectReasons = rejectReasons;
        }
    }

}
