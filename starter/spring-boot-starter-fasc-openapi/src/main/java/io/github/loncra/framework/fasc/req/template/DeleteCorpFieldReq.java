package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.base.BaseReq;

import java.util.List;

/**
 * @author Fadada
 * @date 2023/6/27 17:20
 */
public class DeleteCorpFieldReq extends BaseReq {

    private String openCorpId;

    private List<DeleteCorpFileInfo> fields;

    public String getOpenCorpId() {
        return openCorpId;
    }

    public void setOpenCorpId(String openCorpId) {
        this.openCorpId = openCorpId;
    }

    public List<DeleteCorpFileInfo> getFields() {
        return fields;
    }

    public void setFields(List<DeleteCorpFileInfo> fields) {
        this.fields = fields;
    }
}
