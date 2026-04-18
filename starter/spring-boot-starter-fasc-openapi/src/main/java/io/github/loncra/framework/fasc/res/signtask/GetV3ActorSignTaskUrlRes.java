package io.github.loncra.framework.fasc.res.signtask;

import java.util.List;

public class GetV3ActorSignTaskUrlRes {
    private List<SignUrlRes> signUrls;
    private MiniProgramConfigRes miniProgramConfig;
    private List<SignUrlDetail> signDetails;


    public static class SignUrlRes {
        private String signUrl;

        public String getSignUrl() {
            return signUrl;
        }

        public void setSignUrl(String signUrl) {
            this.signUrl = signUrl;
        }
    }

    public static class MiniProgramConfigRes {
        private String appId;
        private String path;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }


    public static class SignUrlDetail {
        private SignerRes signer;
        private String signUrl;
        private Integer signOrder;
        private Integer signStatus;

        public SignerRes getSigner() {
            return signer;
        }

        public void setSigner(SignerRes signer) {
            this.signer = signer;
        }

        public String getSignUrl() {
            return signUrl;
        }

        public void setSignUrl(String signUrl) {
            this.signUrl = signUrl;
        }

        public Integer getSignOrder() {
            return signOrder;
        }

        public void setSignOrder(Integer signOrder) {
            this.signOrder = signOrder;
        }

        public Integer getSignStatus() {
            return signStatus;
        }

        public void setSignStatus(Integer signStatus) {
            this.signStatus = signStatus;
        }
    }

    public static class SignerRes {
        private SignatoryRes signatory;
        private CorpRes corp;

        public SignatoryRes getSignatory() {
            return signatory;
        }

        public void setSignatory(SignatoryRes signatory) {
            this.signatory = signatory;
        }

        public CorpRes getCorp() {
            return corp;
        }

        public void setCorp(CorpRes corp) {
            this.corp = corp;
        }
    }

    public static class SignatoryRes {
        private String signerId;

        public String getSignerId() {
            return signerId;
        }

        public void setSignerId(String signerId) {
            this.signerId = signerId;
        }
    }

    public static class CorpRes {
        private String corpId;

    }

    public List<SignUrlRes> getSignUrls() {
        return signUrls;
    }

    public void setSignUrls(List<SignUrlRes> signUrls) {
        this.signUrls = signUrls;
    }

    public MiniProgramConfigRes getMiniProgramConfig() {
        return miniProgramConfig;
    }

    public void setMiniProgramConfig(MiniProgramConfigRes miniProgramConfig) {
        this.miniProgramConfig = miniProgramConfig;
    }

    public List<SignUrlDetail> getSignDetails() {
        return signDetails;
    }

    public void setSignDetails(List<SignUrlDetail> signDetails) {
        this.signDetails = signDetails;
    }
}