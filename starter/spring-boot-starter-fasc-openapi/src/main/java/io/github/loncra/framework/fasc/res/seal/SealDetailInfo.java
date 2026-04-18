package io.github.loncra.framework.fasc.res.seal;

import java.util.List;

/**
 * @Author： Fadada
 * @Date: 2022/10/8
 */
public class SealDetailInfo extends SealInfo {
    /**
     * 企业用印员列表
     */
    private List<SealUserDetail> sealUsers;

    /**
     * 授权免验证签信息列表
     */
    private List<FreeSignInfo> freeSignInfos;

    public List<SealUserDetail> getSealUsers() {
        return sealUsers;
    }

    public void setSealUsers(List<SealUserDetail> sealUsers) {
        this.sealUsers = sealUsers;
    }

    public List<FreeSignInfo> getFreeSignInfos() {
        return freeSignInfos;
    }

    public void setFreeSignInfos(List<FreeSignInfo> freeSignInfos) {
        this.freeSignInfos = freeSignInfos;
    }

    public static class FreeSignInfo {
        /**
         * 免验证签场景码
         */
        private String businessId;
        /**
         * 免验证签业务场景
         */
        private String businessScene;

        private String businessSceneExp;

        private String expiresTime;

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getBusinessScene() {
            return businessScene;
        }

        public void setBusinessScene(String businessScene) {
            this.businessScene = businessScene;
        }

        public String getBusinessSceneExp() {
            return businessSceneExp;
        }

        public void setBusinessSceneExp(String businessSceneExp) {
            this.businessSceneExp = businessSceneExp;
        }

        public String getExpiresTime() {
            return expiresTime;
        }

        public void setExpiresTime(String expiresTime) {
            this.expiresTime = expiresTime;
        }
    }
}
