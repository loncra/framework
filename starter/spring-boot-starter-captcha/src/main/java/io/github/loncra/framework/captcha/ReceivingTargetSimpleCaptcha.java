package io.github.loncra.framework.captcha;

import java.io.Serial;

/**
 * 收信目标匹配验证码,在收验证码时，有一个目标信息，如：短信验证码为手机号，邮箱验证码为邮箱，用该类可以得知目标是否一致
 *
 * @author maurice.chen
 */
public class ReceivingTargetSimpleCaptcha extends SimpleCaptcha {

    @Serial
    private static final long serialVersionUID = 3083613530309672558L;

    /**
     * 目标值
     */
    private String target;

    /**
     * 构造函数
     */
    public ReceivingTargetSimpleCaptcha() {
    }

    /**
     * 构造函数
     *
     * @param target 目标值
     */
    public ReceivingTargetSimpleCaptcha(String target) {
        this.target = target;
    }

    /**
     * 获取目标值
     *
     * @return 目标值
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置目标值
     *
     * @param target 目标值
     */
    public void setTarget(String target) {
        this.target = target;
    }
}
