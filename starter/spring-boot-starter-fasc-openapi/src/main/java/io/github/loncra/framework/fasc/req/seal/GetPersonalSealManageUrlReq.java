package io.github.loncra.framework.fasc.req.seal;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

public class GetPersonalSealManageUrlReq extends BaseReq {

    /**
     * 应用系统中唯一确定登录用户身份的标识，用于法大大完成登录后进行帐号映射，便于后续从应用系统免登到法大大
     */
    private String clientUserId;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}



