package io.github.loncra.framework.captcha;

import java.io.Serial;

/**
 * 带匹配值的验证码实现，如：图片验证码，短信验证码，邮箱验证码等类型使用该类做自动匹配
 *
 * @author maurice.chen
 */
public class SimpleCaptcha extends ReusableCaptcha {

    @Serial
    private static final long serialVersionUID = 1623791533763152034L;

    /**
     * 值
     */
    private String value;

    /**
     * 校验成功是否删除
     */
    private boolean verifySuccessDelete = true;

    /**
     * 构造函数
     */
    public SimpleCaptcha() {
    }

    /**
     * 构造函数
     *
     * @param value 验证码值
     */
    public SimpleCaptcha(String value) {
        this.value = value;
    }

    /**
     * 构造函数
     *
     * @param value               验证码值
     * @param verifySuccessDelete 校验成功是否删除
     */
    public SimpleCaptcha(
            String value,
            boolean verifySuccessDelete
    ) {
        this.value = value;
        this.verifySuccessDelete = verifySuccessDelete;
    }

    /**
     * 获取验证码值
     *
     * @return 验证码值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置验证码值
     *
     * @param value 验证码值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 是否校验成功删除
     *
     * @return true 是，否则 false
     */
    public boolean isVerifySuccessDelete() {
        return verifySuccessDelete;
    }

    /**
     * 设置是否校验成功删除
     *
     * @param verifySuccessDelete true 是，否则 false
     */
    public void setVerifySuccessDelete(boolean verifySuccessDelete) {
        this.verifySuccessDelete = verifySuccessDelete;
    }
}
