package io.github.loncra.spring.security.redis.authentication.cache.support;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.spring.security.oauth2.authentication.config.OAuth2Properties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * oauth2 redis 授权服务实现
 *
 * @author maurice.chen
 */
public class OAuth2RedissonAuthorizationService implements OAuth2AuthorizationService {

    private final OAuth2Properties oAuth2Properties;

    private final RedissonClient redissonClient;

    public OAuth2RedissonAuthorizationService(
            RedissonClient redissonClient,
            OAuth2Properties oAuth2Properties
    ) {
        this.redissonClient = redissonClient;
        this.oAuth2Properties = oAuth2Properties;
    }

    @Override
    public void save(OAuth2Authorization authorization) {

        TimeProperties time = oAuth2Properties.getAuthorizationCache().getExpiresTime();

        if (Objects.nonNull(authorization.getAccessToken())) {
            String accessTokenKey = authorization.getAccessToken().getToken().getTokenValue();
            String md5AccessTokenKey = DigestUtils.md5DigestAsHex(accessTokenKey.getBytes(StandardCharsets.UTF_8));
            String cacheKey = oAuth2Properties.getAuthorizationCache().getName(OAuth2ParameterNames.ACCESS_TOKEN + CacheProperties.DEFAULT_SEPARATOR + md5AccessTokenKey);

            RBucket<OAuth2Authorization> accessTokenBucket = redissonClient.getBucket(cacheKey, new SerializationCodec());
            accessTokenBucket.set(authorization);
            if (Objects.nonNull(authorization.getAccessToken().getToken().getExpiresAt())) {
                accessTokenBucket.expireAsync(authorization.getAccessToken().getToken().getExpiresAt());
            }
            else if (Objects.nonNull(time)) {
                accessTokenBucket.expireAsync(time.toDuration());
            }
            remove(authorization, false);
        }
        else if (authorization.getAttributes().containsKey(OAuth2ParameterNames.STATE)) {
            String state = authorization.getAttributes().getOrDefault(OAuth2ParameterNames.STATE, StringUtils.EMPTY).toString();
            String md5State = DigestUtils.md5DigestAsHex(state.getBytes(StandardCharsets.UTF_8));
            String cacheKey = oAuth2Properties.getAuthorizationCache().getName(OAuth2ParameterNames.STATE + CacheProperties.DEFAULT_SEPARATOR + md5State);

            RBucket<OAuth2Authorization> authorizationCodeBucket = redissonClient.getBucket(cacheKey, new SerializationCodec());
            authorizationCodeBucket.set(authorization);
            if (Objects.nonNull(time)) {
                authorizationCodeBucket.expireAsync(time.toDuration());
            }
        }
        else {

            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);
            if (Objects.nonNull(authorizationCode)) {
                String codeKey = authorizationCode.getToken().getTokenValue();
                String md5CodeKey = DigestUtils.md5DigestAsHex(codeKey.getBytes(StandardCharsets.UTF_8));
                String cacheKey = oAuth2Properties.getAuthorizationCache().getName(OAuth2ParameterNames.CODE + CacheProperties.DEFAULT_SEPARATOR + md5CodeKey);

                RBucket<OAuth2Authorization> authorizationCodeBucket = redissonClient.getBucket(cacheKey, new SerializationCodec());
                authorizationCodeBucket.set(authorization);
                if (Objects.nonNull(authorizationCode.getToken().getExpiresAt())) {
                    authorizationCodeBucket.expireAsync(authorizationCode.getToken().getExpiresAt());
                }
                else if (Objects.nonNull(time)) {
                    authorizationCodeBucket.expireAsync(time.toDuration());
                }
            }

        }

        if (Objects.nonNull(authorization.getRefreshToken())) {
            String refreshKey = authorization.getRefreshToken().getToken().getTokenValue();
            String md5RefreshKey = DigestUtils.md5DigestAsHex(refreshKey.getBytes(StandardCharsets.UTF_8));
            String cacheKey = oAuth2Properties.getAuthorizationCache().getName(OAuth2ParameterNames.REFRESH_TOKEN + CacheProperties.DEFAULT_SEPARATOR + md5RefreshKey);

            RBucket<OAuth2Authorization> refreshTokenBucket = redissonClient.getBucket(cacheKey, new SerializationCodec());
            refreshTokenBucket.set(authorization);
            if (Objects.nonNull(authorization.getRefreshToken().getToken().getExpiresAt())) {
                refreshTokenBucket.expireAsync(authorization.getRefreshToken().getToken().getExpiresAt());
            }
            else if (Objects.nonNull(time)) {
                refreshTokenBucket.expireAsync(time.toDuration());
            }
        }

    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        remove(authorization, true);
    }

    public void remove(
            OAuth2Authorization authorization,
            boolean removeIdCache
    ) {

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization.getToken(OAuth2AuthorizationCode.class);

        if (Objects.nonNull(authorizationCode)) {
            String codeKey = authorizationCode.getToken().getTokenValue();
            String md5CodeKey = DigestUtils.md5DigestAsHex(codeKey.getBytes(StandardCharsets.UTF_8));
            String cacheKey = oAuth2Properties.getAuthorizationCache().getName(OAuth2ParameterNames.CODE + CacheProperties.DEFAULT_SEPARATOR + md5CodeKey);
            redissonClient.getBucket(cacheKey).deleteAsync();
        }

        if (authorization.getAttributes().containsKey(OAuth2ParameterNames.STATE)) {
            String state = authorization.getAttributes().getOrDefault(OAuth2ParameterNames.STATE, StringUtils.EMPTY).toString();
            String md5State = DigestUtils.md5DigestAsHex(state.getBytes(StandardCharsets.UTF_8));
            String cacheKey = oAuth2Properties.getAuthorizationCache().getName(OAuth2ParameterNames.STATE + CacheProperties.DEFAULT_SEPARATOR + md5State);

            redissonClient.getBucket(cacheKey).deleteAsync();
        }

        if (removeIdCache) {
            String key = oAuth2Properties.getAuthorizationCache().getName(authorization.getId());
            redissonClient.getBucket(key).deleteAsync();
        }
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException("redisson 的 oauth 授权服务实现, 不支持 public OAuth2Authorization findById(String id) 操作");
    }

    @Override
    public OAuth2Authorization findByToken(
            String token,
            OAuth2TokenType tokenType
    ) {

        String key;

        if (Objects.nonNull(tokenType)) {
            key = tokenType.getValue() + CacheProperties.DEFAULT_SEPARATOR + DigestUtils.md5DigestAsHex(token.getBytes());
        }
        else {
            key = DigestUtils.md5DigestAsHex(token.getBytes());
        }

        String cacheKey = oAuth2Properties.getAuthorizationCache().getName(key);
        RBucket<OAuth2Authorization> bucket = redissonClient.getBucket(cacheKey, new SerializationCodec());

        if (OAuth2ParameterNames.STATE.contains(tokenType.getValue())) {
            return bucket.getAndDelete();
        }

        return bucket.get();
    }

}
