package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author Fadada
 * @date 2023/6/27 17:19
 */
public class CreateCorpFieldReq extends BaseReq {

    private String openCorpId;

    private List<CreateCorpFieldInfo> fields;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public List<CreateCorpFieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<CreateCorpFieldInfo> fields) {
        this.fields = fields;
    }
}
