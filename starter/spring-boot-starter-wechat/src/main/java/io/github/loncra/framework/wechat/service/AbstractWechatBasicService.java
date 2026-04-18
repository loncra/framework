package io.github.loncra.framework.wechat.service;

import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.annotation.Time;
import io.github.loncra.framework.commons.domain.AccessToken;
import io.github.loncra.framework.commons.domain.metadata.RefreshAccessTokenMetadata;
import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.idempotent.ConcurrentConfig;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.idempotent.annotation.Concurrent;
import io.github.loncra.framework.nacos.task.annotation.NacosCronScheduled;
import io.github.loncra.framework.wechat.WechatProperties;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.redisson.api.RBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 抽象的微信基础服务实现
 *
 * @author maurice.chen
 */
public abstract class AbstractWechatBasicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWechatBasicService.class);

    /**
     * 微信配置属性
     */
    private final WechatProperties wechatProperties;

    /**
     * REST 模板
     */
    private final RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

    /**
     * 并发拦截器
     */
    private final ConcurrentInterceptor concurrentInterceptor;

    /**
     * 构造函数
     *
     * @param wechatProperties      微信配置属性
     * @param concurrentInterceptor 并发拦截器
     */
    public AbstractWechatBasicService(
            WechatProperties wechatProperties,
            ConcurrentInterceptor concurrentInterceptor
    ) {
        this.wechatProperties = wechatProperties;
        this.concurrentInterceptor = concurrentInterceptor;
    }

    /**
     * 获取刷新访问令牌的元数据
     *
     * @return 刷新访问令牌的元数据
     */
    protected abstract RefreshAccessTokenMetadata getRefreshAccessTokenMetadata();

    /**
     * 获取访问令牌，如果缓存为空则刷新
     *
     * @return 访问令牌
     */
    public AccessToken getAccessTokenIfCacheNull() {
        RBucket<AccessToken> bucket = concurrentInterceptor.getRedissonClient().getBucket(getRefreshAccessTokenMetadata().getCache().getName());
        AccessToken accessToken = bucket.get();
        if (Objects.isNull(accessToken)) {
            accessToken = refreshAccessToken();
        }

        return accessToken;
    }

    /**
     * 刷新访问令牌
     * <p>如果缓存中的令牌仍然有效，则直接返回；否则从微信 API 获取新令牌并缓存</p>
     *
     * @return 访问令牌
     */
    @NacosCronScheduled(cron = "${loncra.framework.wechat.refresh-access-token-cron:0 0/30 * * * ? }")
    @Concurrent(value = "loncra:framework:wechat:refresh-access-token", waitTime = @Time(value = 8, unit = TimeUnit.SECONDS), leaseTime = @Time(value = 5, unit = TimeUnit.SECONDS), exception = "刷新微信 accessToken 出现并发")
    public AccessToken refreshAccessToken() {
        RefreshAccessTokenMetadata refreshAccessTokenMetadata = getRefreshAccessTokenMetadata();
        RBucket<AccessToken> bucket = concurrentInterceptor
                .getRedissonClient()
                .getBucket(refreshAccessTokenMetadata.getCache().getName());
        AccessToken token = bucket.get();
        if (Objects.nonNull(token) && Duration.between(token.getCreationTime(), Instant.now()).toMillis() <= refreshAccessTokenMetadata.getRefreshAccessTokenLeadTime().toMillis()) {
            return token;
        }
        ConcurrentConfig config = new ConcurrentConfig();

        config.setKey(refreshAccessTokenMetadata.getCache().getConcurrentName());
        config.setException("获取微信 accessToken 出现并发");
        config.setWaitTime(TimeProperties.of(8, TimeUnit.SECONDS));
        config.setLeaseTime(TimeProperties.of(5, TimeUnit.SECONDS));

        token = concurrentInterceptor.invoke(config, this::getAccessToken);
        bucket.set(token, token.getExpiresTime().toDuration());

        return token;
    }

    /**
     * 从缓存获取访问令牌
     *
     * @return 访问令牌，如果缓存中没有则返回 null
     */
    private AccessToken getCacheAccessToken() {
        RefreshAccessTokenMetadata refreshAccessTokenMetadata = getRefreshAccessTokenMetadata();
        RBucket<AccessToken> bucket = concurrentInterceptor
                .getRedissonClient()
                .getBucket(refreshAccessTokenMetadata.getCache().getName());
        return bucket.get();
    }

    /**
     * 获取微信访问 token
     *
     * @return 访问 token
     */
    private AccessToken getAccessToken() {
        AccessToken token = getCacheAccessToken();
        if (Objects.nonNull(token)) {
            return token;
        }

        Map<String, String> param = new LinkedHashMap<>();
        param.put("appid", getRefreshAccessTokenMetadata().getSecretId());
        param.put("secret", getRefreshAccessTokenMetadata().getSecretKey());
        param.put("grant_type", "client_credential");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Map<String, Object>> result = restTemplate.exchange(
                "https://api.weixin.qq.com/cgi-bin/stable_token",
                HttpMethod.POST,
                new HttpEntity<>(param, httpHeaders),
                new ParameterizedTypeReference<>() {
                }
        );
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("获取 wechat access token 结果为:{}", result.getBody());
        }
        if (isSuccess(result) && Objects.requireNonNull(result.getBody()).containsKey("access_token")) {
            token = new AccessToken();
            token.setValue(result.getBody().get("access_token").toString());
            int expires = NumberUtils.toInt(result.getBody().get("expires_in").toString()) / 2;
            token.setExpiresTime(TimeProperties.of(expires, TimeUnit.SECONDS));
        }
        else {
            throwSystemExceptionIfError(result.getBody());
        }

        return token;
    }

    /**
     * 是否调用成功
     *
     * @param result 响应实体
     *
     * @return true 是，否则 false
     */
    public boolean isSuccess(ResponseEntity<Map<String, Object>> result) {

        if (!HttpStatus.OK.equals(result.getStatusCode())) {
            return false;
        }

        if (MapUtils.isEmpty(result.getBody())) {
            return false;
        }

        if (result.getBody().containsKey(wechatProperties.getStatusCodeFieldName())) {
            String statusCode = result.getBody().get(wechatProperties.getStatusCodeFieldName()).toString();
            return statusCode.equals(wechatProperties.getSuccessCodeValue());
        }

        return true;
    }

    /**
     * 如果响应内容错误，抛出异常
     *
     * @param result 响应数据
     */
    public void throwSystemExceptionIfError(Map<String, Object> result) {
        if (MapUtils.isNotEmpty(result)) {
            throw new ErrorCodeException(result.get(wechatProperties.getStatusMessageFieldName()).toString(), result.get(wechatProperties.getStatusCodeFieldName()).toString());
        }
        else {
            throw new SystemException("执行微信 api 接口出现异常");
        }
    }

    /**
     * 获取微信配置
     *
     * @return 微信配置属性
     */
    public WechatProperties getWechatConfig() {
        return wechatProperties;
    }

    /**
     * 获取 REST 模板
     *
     * @return REST 模板
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * 获取并发拦截器
     *
     * @return 并发拦截器
     */
    public ConcurrentInterceptor getConcurrentInterceptor() {
        return concurrentInterceptor;
    }
}
