package io.github.loncra.framework.fasc.bean.common;

import io.github.loncra.framework.fasc.bean.base.BaseBean;

import java.util.List;

/**
 * @author Fadada
 * @date 2021/12/6 9:46:32
 */
public class TemplateFieldMultiCheckBox extends BaseBean {
    private Boolean required;
    private List<Boolean> defaultValue;
    private String option;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<Boolean> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(List<Boolean> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
