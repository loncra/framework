package io.github.loncra.framework.fasc.res.template;


import io.github.loncra.framework.fasc.bean.base.BaseBean;

/**
 * @Author： zhupintian
 * @Date: 2023/7/17
 */
public class CreateDocTemplateRes extends BaseBean {

    /**
     * 模板id
     */
    private String docTemplateId;

    /**
     * 模板创建序列号
     */
    private String createSerialNo;

    public String getDocTemplateId() {
        return docTemplateId;
    }

    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    public String getCreateSerialNo() {
        return createSerialNo;
    }

    public void setCreateSerialNo(String createSerialNo) {
        this.createSerialNo = createSerialNo;
    }
}
