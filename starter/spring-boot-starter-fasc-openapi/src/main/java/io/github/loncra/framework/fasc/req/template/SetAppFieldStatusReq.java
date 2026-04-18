package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:53
 */
public class SetAppFieldStatusReq extends BaseReq {
    private String fieldKey;
    private String fieldStatus;

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldStatus() {
        return fieldStatus;
    }

    public void setFieldStatus(String fieldStatus) {
        this.fieldStatus = fieldStatus;
    }
}
