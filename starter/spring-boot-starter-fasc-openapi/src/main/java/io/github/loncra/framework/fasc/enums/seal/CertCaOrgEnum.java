package io.github.loncra.framework.fasc.enums.seal;

/**
 * @author Fadada
 * @date 2021/12/16 10:22:33
 */
public enum CertCaOrgEnum {

    CFCA("CFCA", "中国金融认证中心"),
    ZXCA("ZXCA", "山东豸信认证服务有限公司"),
    CSCA("CSCA", "世纪数码CA"),
    ;

    private String code;
    private String remark;

    CertCaOrgEnum(
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
