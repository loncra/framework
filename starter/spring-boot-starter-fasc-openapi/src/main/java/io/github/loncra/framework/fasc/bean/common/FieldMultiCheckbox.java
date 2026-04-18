package io.github.loncra.framework.fasc.bean.common;

import java.util.List;

public class FieldMultiCheckbox {

    private Boolean required;
    private List<String> option;
    private List<Boolean> defaultValue;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public List<Boolean> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(List<Boolean> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
