package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @author Fadada
 * @date 2021/12/6 9:28:18
 */
public class OpenId extends BaseBean {
    private String idType;
    private String openId;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public static OpenId getInstance(
            String idType,
            String id
    ) {
        OpenId openId = new OpenId();
        openId.setIdType(idType);
        openId.setOpenId(id);
        return openId;
    }
}
