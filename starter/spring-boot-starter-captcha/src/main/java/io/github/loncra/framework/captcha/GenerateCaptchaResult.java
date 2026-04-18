package io.github.loncra.framework.captcha;

import java.io.Serial;
import java.io.Serializable;

/**
 * 生成验证码结果集
 *
 * @author maurice
 */
public class GenerateCaptchaResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1739665352581051182L;
    /**
     * 结果集
     */
    private final Object result;

    /**
     * 要匹配的验证码值
     */
    private final String matchValue;

    /**
     * 构造函数
     *
     * @param result     结果集
     * @param matchValue 要匹配的验证码值
     */
    public GenerateCaptchaResult(
            Object result,
            String matchValue
    ) {
        this.result = result;
        this.matchValue = matchValue;
    }

    /**
     * 获取结果集
     *
     * @return 结果集
     */
    public Object getResult() {
        return result;
    }

    /**
     * 获取要匹配的验证码值
     *
     * @return 要匹配的验证码值
     */
    public String getMatchValue() {
        return matchValue;
    }

    /**
     * 创建生成验证码结果
     *
     * @param result     结果集
     * @param matchValue 要匹配的验证码值
     *
     * @return 生成验证码结果
     */
    public static GenerateCaptchaResult of(
            Object result,
            String matchValue
    ) {
        return new GenerateCaptchaResult(result, matchValue);
    }
}
