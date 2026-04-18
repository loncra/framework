package io.github.loncra.framework.fasc.enums.user;

/**
 * @author Fadada
 * 2021/10/23 16:08:40
 */
public enum UserIdentMethodEnum {
    /**
     * 用户实名认证时所选择的认证方案：
     * mobile: 实名手机号认证 (该方案校验的是用户在电信运营商绑定的真实姓名、身份证号码、手机号及对应的短信验证码)
     * face: 人脸识别认证 (该方案校验的是用户的真实姓名、身份证号码和人脸比对)
     * bank: 个人银行卡认证 (该方案校验的是用户在银行绑定的真实姓名、身份证号码、银行卡卡号、预留在银行的手机号及对应的短信验证码)
     * offline: 人工审核认证 (该方案主要为特殊人群提供的人工审核通道，如部分用户因为特殊原因，无法在其他方案下完成认证。例如：手机不可用、人脸比对不成功、外国人等)。
     */

    MOBILE("mobile", "实名手机号认证"),
    FACE("face", "人脸识别认证"),
    BANK("bank", "个人银行卡认证"),
    OFFLINE("offline", "人工审核认证");

    private String code;
    private String remark;

    UserIdentMethodEnum(
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
