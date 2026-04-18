package io.github.loncra.framework.fasc.req.seal;


import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

public class GetPersonalSealCreateUrlReq extends BaseReq {

    /**
     * 应用系统中唯一确定登录用户身份的标识，用于法大大完成登录后进行帐号映射，便于后续从应用系统免登到法大大
     */
    private String clientUserId;

    /**
     * 业务系统定义的印章创建序列号：用于在印章创建后的回调事件中将sealId和createSerialNo进行对应
     */
    private String createSerialNo;

    private List<String> createMethod;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    public List<String> getCreateMethod() {
        return createMethod;
    }

    public void setCreateMethod(List<String> createMethod) {
        this.createMethod = createMethod;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}



