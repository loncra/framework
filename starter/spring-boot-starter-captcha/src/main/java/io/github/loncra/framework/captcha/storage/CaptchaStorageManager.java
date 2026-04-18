package io.github.loncra.framework.captcha.storage;

import io.github.loncra.framework.captcha.SimpleCaptcha;
import io.github.loncra.framework.captcha.token.BuildToken;
import io.github.loncra.framework.captcha.token.InterceptToken;

/**
 * 验证码存储管理器
 *
 * @author maurice.chen
 */
public interface CaptchaStorageManager {

    /**
     * 保存绑定 token
     *
     * @param token 绑定 token
     */
    void saveBuildToken(BuildToken token);

    /**
     * 保存拦截token
     *
     * @param interceptToken 拦截 token
     */
    void saveInterceptToken(InterceptToken interceptToken);

    /**
     * 获取绑定 token
     *
     * @param token token 值
     * @param type  token 类型
     *
     * @return 绑定 token
     */
    BuildToken getBuildToken(
            String type,
            String token
    );

    /**
     * 获取拦截 token
     *
     * @param token token 值
     *
     * @return 拦截 token
     */
    InterceptToken getInterceptToken(String token);

    /**
     * 删除绑定 token
     *
     * @param buildToken 绑定 token
     */
    void deleteBuildToken(BuildToken buildToken);

    /**
     * 保存带匹配值的验证码
     *
     * @param captcha        带匹配值的验证码
     * @param interceptToken 拦截 token
     */
    void saveCaptcha(
            SimpleCaptcha captcha,
            InterceptToken interceptToken
    );

    /**
     * 获取带匹配值的验证码
     *
     * @param interceptToken token 信息
     *
     * @return 带匹配值的验证码实现
     */
    SimpleCaptcha getCaptcha(InterceptToken interceptToken);

    /**
     * 删除验证码
     *
     * @param token token 信息
     */
    void deleteCaptcha(InterceptToken token);
}
