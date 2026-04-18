package io.github.loncra.framework.captcha.token;

/**
 * 验证码的绑定 token，主要用于让系统知道“谁”在用验证码服务，
 * 并针对每个验证码类型提供自己的构建验证码参数给前端自己通过参数信息去构建指定的验证码信息
 *
 * @author maurice
 */
public interface BuildToken extends InterceptToken {

    /**
     * 设置拦截 token
     *
     * @param token 绑定 token
     */
    void setInterceptToken(InterceptToken token);

    /**
     * 获取拦截 token
     *
     * @return 拦截 token
     */
    InterceptToken getInterceptToken();
}
