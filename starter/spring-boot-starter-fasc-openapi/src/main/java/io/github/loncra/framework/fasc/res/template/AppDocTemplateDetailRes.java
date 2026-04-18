package io.github.loncra.framework.fasc.res.template;

import io.github.loncra.framework.fasc.bean.base.BaseBean;
import io.github.loncra.framework.fasc.bean.common.Field;

import java.util.List;

/**
 * @author zhoufucheng
 * @date 2022/12/25 0025 19:19
 */
public class AppDocTemplateDetailRes extends BaseBean {
    private String appDocTemplateId;
    private String appDocTemplateName;
    private String appDocTemplateStatus;
    private List<Field> docFields;

    public String getAppDocTemplateId() {
        return appDocTemplateId;
    }

    public void setAppDocTemplateId(String appDocTemplateId) {
        this.appDocTemplateId = appDocTemplateId;
    }

    public String getAppDocTemplateName() {
        return appDocTemplateName;
    }

    public void setAppDocTemplateName(String appDocTemplateName) {
        this.appDocTemplateName = appDocTemplateName;
    }

    public String getAppDocTemplateStatus() {
        return appDocTemplateStatus;
    }

    public void setAppDocTemplateStatus(String appDocTemplateStatus) {
        this.appDocTemplateStatus = appDocTemplateStatus;
    }

    public List<Field> getDocFields() {
        return docFields;
    }

    public void setDocFields(List<Field> docFields) {
        this.docFields = docFields;
    }
}
