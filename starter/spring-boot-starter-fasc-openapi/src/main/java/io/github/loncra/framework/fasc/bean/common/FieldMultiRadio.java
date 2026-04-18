package io.github.loncra.framework.fasc.bean.common;

import java.util.List;

public class FieldMultiRadio {

    private Boolean required;
    private List<Boolean> defaultValue;
    private String option;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<Boolean> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(List<Boolean> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
