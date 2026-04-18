package io.github.loncra.framework.fasc.enums.eui;

/**
 * @author Fadada
 * @date 2021/12/18 13:52:18
 */
public enum UrlTypeEnum {

    /**
     * 计费页面类型：
     * account: 账户信息
     * order: 套餐订购
     */
    ACCOUNT("account", "账户信息"),
    ORDER("order", "套餐订购");

    private String code;
    private String remark;

    UrlTypeEnum(
            String code,
            String remark
    ) {
        this.code = code;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public String getRemark() {
        return remark;
    }
}
