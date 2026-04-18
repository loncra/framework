package io.github.loncra.framework.fasc.res.template;

/**
 * @author Fadada
 * @date 2023/6/28 11:16
 */
public class CreateCorpFieldFailInfo {


    private String fieldName;

    private String fieldType;

    /**
     * 失败原因
     */
    private String failReason;

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

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
