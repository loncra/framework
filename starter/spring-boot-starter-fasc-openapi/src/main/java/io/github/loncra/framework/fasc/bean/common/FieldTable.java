package io.github.loncra.framework.fasc.bean.common;

import java.util.List;

public class FieldTable {
    private Boolean required;

    private List<String> header;

    private Integer requiredCount;

    private String fontType;

    private Integer fontSize;

    private String alignment;

    private String headerPosition;

    private Integer rows;

    private Integer cols;

    private Integer rowHeight;

    private List<Integer> widths;

    private Boolean dynamicFilling;

    private List<List<String>> defaultValue;

    private Boolean hideHeader;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public Integer getRequiredCount() {
        return requiredCount;
    }

    public void setRequiredCount(Integer requiredCount) {
        this.requiredCount = requiredCount;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getHeaderPosition() {
        return headerPosition;
    }

    public void setHeaderPosition(String headerPosition) {
        this.headerPosition = headerPosition;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Integer getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(Integer rowHeight) {
        this.rowHeight = rowHeight;
    }

    public List<Integer> getWidths() {
        return widths;
    }

    public void setWidths(List<Integer> widths) {
        this.widths = widths;
    }

    public Boolean getDynamicFilling() {
        return dynamicFilling;
    }

    public void setDynamicFilling(Boolean dynamicFilling) {
        this.dynamicFilling = dynamicFilling;
    }

    public List<List<String>> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(List<List<String>> defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getHideHeader() {
        return hideHeader;
    }

    public void setHideHeader(Boolean hideHeader) {
        this.hideHeader = hideHeader;
    }
}
