package io.github.loncra.framework.fasc.req.template;

import io.github.loncra.framework.fasc.bean.common.FieldMultiCheckbox;
import io.github.loncra.framework.fasc.bean.common.FieldTextMultiLine;
import io.github.loncra.framework.fasc.bean.common.FieldTextSingleLine;

/**
 * @author Fadada
 * @date 2023/6/27 17:26
 */
public class CreateCorpFieldInfo {

    private String fieldName;

    private String fieldType;

    private FieldTextSingleLine fieldTextSingleLine;

    private FieldTextMultiLine fieldTextMultiLine;

    private FieldMultiCheckbox fieldMultiCheckbox;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public FieldTextSingleLine getFieldTextSingleLine() {
        return fieldTextSingleLine;
    }

    public void setFieldTextSingleLine(FieldTextSingleLine fieldTextSingleLine) {
        this.fieldTextSingleLine = fieldTextSingleLine;
    }

    public FieldTextMultiLine getFieldTextMultiLine() {
        return fieldTextMultiLine;
    }

    public void setFieldTextMultiLine(FieldTextMultiLine fieldTextMultiLine) {
        this.fieldTextMultiLine = fieldTextMultiLine;
    }

    public FieldMultiCheckbox getFieldMultiCheckbox() {
        return fieldMultiCheckbox;
    }

    public void setFieldMultiCheckbox(FieldMultiCheckbox fieldMultiCheckbox) {
        this.fieldMultiCheckbox = fieldMultiCheckbox;
    }
}
