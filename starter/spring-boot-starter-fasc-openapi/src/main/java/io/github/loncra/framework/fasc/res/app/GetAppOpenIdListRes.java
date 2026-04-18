package io.github.loncra.framework.fasc.res.app;

import io.github.loncra.framework.fasc.bean.base.BasePageRes;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2023/12/15 14:34
 */
public class GetAppOpenIdListRes extends BasePageRes {
    private List<OpenIdInfo> openIdInfos;

    public List<OpenIdInfo> getOpenIdInfos() {
        return openIdInfos;
    }

    public void setOpenIdInfos(List<OpenIdInfo> openIdInfos) {
        this.openIdInfos = openIdInfos;
    }

    public static class OpenIdInfo {
        private String name;
        private String openId;
        private String clientId;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
    }
}
