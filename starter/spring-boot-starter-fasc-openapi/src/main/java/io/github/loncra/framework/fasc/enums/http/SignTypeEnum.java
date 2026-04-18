package io.github.loncra.framework.fasc.enums.http;

/**
 * @author Fadada
 * 2021/9/8 16:09:38
 */
public enum SignTypeEnum {
    /**
     * SHA256
     */
    HMAC_SHA256("HMAC-SHA256");

    private String type;

    SignTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
