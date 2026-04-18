package io.github.loncra.framework.fasc.enums.common;

/**
 * @author Fadada
 * @date 2021/12/15 14:10:15
 */
public enum AuthResultEnum {
    /**
     * 本次授权操作结果
     * success: 成功；
     * fail: 失败。
     */

    SUCCESS("success", "成功"),
    FAIL("fail", "失败");

    private String code;
    private String remark;

    AuthResultEnum(
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
