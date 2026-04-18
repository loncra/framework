package io.github.loncra.framework.fasc.enums.seal;

/**
 * @author Fadada
 * @date 2021/12/16 10:22:33
 */
public enum CertEncryptTypeEnum {

    SM2("SM2", "国密证书"),
    RSA("RSA", "标准证书"),
    ;

    private String code;
    private String remark;

    CertEncryptTypeEnum(
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
