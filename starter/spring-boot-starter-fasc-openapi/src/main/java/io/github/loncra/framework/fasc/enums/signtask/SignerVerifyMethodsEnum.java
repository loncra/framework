package io.github.loncra.framework.fasc.enums.signtask;

public enum SignerVerifyMethodsEnum {
    PW("pw", "签署密码验证"),
    SMS("sms", "短信验证码"),
    FACE("face", "刷脸验证"),
    AUDIO_VIDEO("audio_video", "互动视频签");


    private String code;
    private String remark;

    SignerVerifyMethodsEnum(
            String code,
            String remark
    ) {
        this.code = code;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
