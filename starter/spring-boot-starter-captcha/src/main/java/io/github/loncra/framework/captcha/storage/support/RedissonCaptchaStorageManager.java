package io.github.loncra.framework.captcha.storage.support;

import io.github.loncra.framework.captcha.CaptchaProperties;
import io.github.loncra.framework.captcha.SimpleCaptcha;
import io.github.loncra.framework.captcha.storage.CaptchaStorageManager;
import io.github.loncra.framework.captcha.token.BuildToken;
import io.github.loncra.framework.captcha.token.InterceptToken;
import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.Objects;

/**
 * redisson 验证码存储管理实现
 *
 * @author maurice.chen
 */
public class RedissonCaptchaStorageManager implements CaptchaStorageManager {

    /**
     * Redisson 客户端
     */
    private final RedissonClient redissonClient;

    /**
     * 验证码配置属性
     */
    private final CaptchaProperties captchaProperties;

    /**
     * 默认的验证码名称
     */
    public static final String DEFAULT_CAPTCHA_NAME = "captcha";

    /**
     * 创建一个 Redisson 验证码存储管理器
     *
     * @param redissonClient    Redisson 客户端
     * @param captchaProperties 验证码配置属性
     */
    public RedissonCaptchaStorageManager(
            RedissonClient redissonClient,
            CaptchaProperties captchaProperties
    ) {
        this.redissonClient = redissonClient;
        this.captchaProperties = captchaProperties;
    }

    /**
     * 获取绑定 token 的存储桶
     *
     * @param type  token 类型
     * @param token token 值
     *
     * @return 存储桶
     */
    protected RBucket<BuildToken> getBuildTokenBucket(
            String type,
            String token
    ) {
        return redissonClient.getBucket(captchaProperties.getBuildTokenCache().getName(type + CacheProperties.DEFAULT_SEPARATOR + token));
    }

    /**
     * 获取拦截 token 的存储桶
     *
     * @param token token 值
     *
     * @return 存储桶
     */
    protected RBucket<InterceptToken> getInterceptTokenBucket(String token) {
        return redissonClient.getBucket(captchaProperties.getInterceptorTokenCache().getName(token));
    }

    /**
     * 获取验证码的存储桶
     *
     * @param token token 值
     *
     * @return 存储桶
     */
    protected RBucket<SimpleCaptcha> getSimpleCaptchaBucket(String token) {
        String name = captchaProperties
                .getBuildTokenCache()
                .getName(DEFAULT_CAPTCHA_NAME + CacheProperties.DEFAULT_SEPARATOR + token);
        return redissonClient.getBucket(name);
    }

    @Override
    public void saveBuildToken(BuildToken token) {
        RBucket<BuildToken> bucket = getBuildTokenBucket(token.getType(), token.getToken().getName());
        TimeProperties timeProperties = captchaProperties.getBuildTokenCache().getExpiresTime();
        if (Objects.nonNull(timeProperties)) {
            bucket.setAsync(token, timeProperties.getValue(), timeProperties.getUnit());
        }
        else {
            bucket.setAsync(token);
        }
    }

    @Override
    public void saveInterceptToken(InterceptToken interceptToken) {
        RBucket<InterceptToken> bucket = getInterceptTokenBucket(interceptToken.getToken().getName());
        TimeProperties timeProperties = captchaProperties.getInterceptorTokenCache().getExpiresTime();
        if (Objects.nonNull(timeProperties)) {
            bucket.setAsync(interceptToken, timeProperties.getValue(), timeProperties.getUnit());
        }
        else {
            bucket.setAsync(interceptToken);
        }
    }

    @Override
    public BuildToken getBuildToken(
            String type,
            String token
    ) {
        return getBuildTokenBucket(type, token).get();
    }

    @Override
    public InterceptToken getInterceptToken(String token) {
        return getInterceptTokenBucket(token).get();
    }

    @Override
    public void deleteBuildToken(BuildToken buildToken) {
        getBuildTokenBucket(buildToken.getType(), buildToken.getToken().getName()).deleteAsync();
        if (Objects.isNull(buildToken.getInterceptToken())) {
            return;
        }
        getInterceptTokenBucket(buildToken.getInterceptToken().getToken().getName()).deleteAsync();
    }

    @Override
    public void saveCaptcha(
            SimpleCaptcha captcha,
            InterceptToken interceptToken
    ) {
        RBucket<SimpleCaptcha> bucket = getSimpleCaptchaBucket(interceptToken.obtainTokenName());
        TimeProperties timeProperties = captchaProperties.getInterceptorTokenCache().getExpiresTime();
        if (Objects.nonNull(timeProperties)) {
            bucket.setAsync(captcha, timeProperties.getValue(), timeProperties.getUnit());
        }
        else {
            bucket.setAsync(captcha);
        }
    }

    @Override
    public SimpleCaptcha getCaptcha(InterceptToken interceptToken) {
        return getSimpleCaptchaBucket(interceptToken.obtainTokenName()).get();
    }

    @Override
    public void deleteCaptcha(InterceptToken interceptToken) {
        getSimpleCaptchaBucket(interceptToken.obtainTokenName()).deleteAsync();
    }
}
