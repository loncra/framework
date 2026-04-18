package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;
import io.github.loncra.framework.fasc.bean.common.OpenId;

/**
 * @author Fadada
 * 2021/9/11 15:16:27
 */
public class SignTemplateDetailReq extends BaseReq {
    private OpenId ownerId;
    private String signTemplateId;

    public OpenId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OpenId ownerId) {
        this.ownerId = ownerId;
    }

    public String getSignTemplateId() {
        return signTemplateId;
    }

    public void setSignTemplateId(String signTemplateId) {
        this.signTemplateId = signTemplateId;
    }
}

