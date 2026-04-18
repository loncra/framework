package io.github.loncra.framework.spring.security.session.token;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.id.IdEntity;
import io.github.loncra.framework.crypto.CipherAlgorithmService;
import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.crypto.algorithm.ByteSource;
import io.github.loncra.framework.crypto.algorithm.cipher.CipherService;
import io.github.loncra.framework.crypto.algorithm.exception.CryptoException;
import io.github.loncra.framework.security.audit.IdAuditEvent;
import io.github.loncra.framework.security.entity.SecurityPrincipal;
import io.github.loncra.framework.spring.security.core.authentication.AccessTokenContextRepository;
import io.github.loncra.framework.spring.security.core.authentication.cache.CacheManager;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.token.AuditAuthenticationToken;
import io.github.loncra.framework.spring.security.core.entity.AccessTokenDetails;
import io.github.loncra.framework.spring.security.core.entity.support.MobileSecurityPrincipal;
import io.github.loncra.framework.spring.security.session.config.SessionProperties;
import io.github.loncra.framework.spring.web.device.DeviceUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

/**
 * 访问令牌上下文仓库实现，用于移动端用户明细登录系统后，返回一个 token，在无状态的 HTTP 传输中，通过该 token 来完成认证授权等所有工作
 *
 * @author maurice.chen
 */
public class SessionAccessTokenContextRepository extends HttpSessionSecurityContextRepository implements AccessTokenContextRepository {

    /**
     * 日志记录器
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(SessionAccessTokenContextRepository.class);

    /**
     * 缓存管理器
     */
    private final CacheManager cacheManager;

    /**
     * 访问令牌配置属性
     */
    private final AuthenticationProperties authenticationProperties;

    private final SessionProperties sessionProperties;

    /**
     * 登录请求匹配器
     */
    private final PathPatternRequestMatcher loginRequestMatcher;

    /**
     * 加密算法服务
     */
    private final CipherAlgorithmService cipherAlgorithmService = new CipherAlgorithmService();

    /**
     * 构造函数
     *
     * @param cacheManager 缓存管理器
     * @param sessionProperties session 配置
     * @param authenticationProperties 认证配置属性
     */
    public SessionAccessTokenContextRepository(CacheManager cacheManager,
                                               SessionProperties sessionProperties,
                                               AuthenticationProperties authenticationProperties) {
        this.cacheManager = cacheManager;
        this.sessionProperties = sessionProperties;
        this.authenticationProperties = authenticationProperties;
        loginRequestMatcher = PathPatternRequestMatcher
                .withDefaults()
                .matcher(HttpMethod.POST, this.authenticationProperties.getLoginProcessingUrl());
    }

    /**
     * 延迟加载安全上下文
     *
     * @param request HTTP 请求
     * @return 延迟安全上下文
     */
    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        DeferredSecurityContext context =  super.loadDeferredContext(request);
        if (Objects.nonNull(context.get().getAuthentication())) {
            return context;
        }

        Field field = ReflectionUtils.findField(this.getClass(), "securityContextHolderStrategy");
        SecurityContextHolderStrategy securityContextHolderStrategy = null;
        if (Objects.nonNull(field)) {
            field.setAccessible(true);
            securityContextHolderStrategy = CastUtils.cast(ReflectionUtils.getField(field, this));
        }

        return new SessionAccessTokenDeferredSecurityContext(() -> readSecurityContextFromRequest(request), securityContextHolderStrategy);
    }

    /**
     * 加载安全上下文
     *
     * @param requestResponseHolder HTTP 请求响应持有者
     * @return 安全上下文
     */
    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        HttpServletRequest request = requestResponseHolder.getRequest();
        SecurityContext securityContext = readSecurityContextFromRequest(request);

        if (Objects.isNull(securityContext)) {
            securityContext = super.loadContext(requestResponseHolder);
        }

        return securityContext;
    }

    /**
     * 从 HTTP 请求中读取安全上下文
     *
     * @param request HTTP 请求
     * @return 安全上下文
     */
    private SecurityContext readSecurityContextFromRequest(HttpServletRequest request) {
        if (this.loginRequestMatcher.matches(request)) {
            return null;
        }
        String token = getAccessToken(request);
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        return getSecurityContext(token);
    }

    /**
     * 根据访问令牌获取安全上下文
     *
     * @param token 访问令牌
     * @return 安全上下文
     */
    @Override
    public SecurityContext getSecurityContext(String token) {

        if (StringUtils.isEmpty(token) || !Base64.isBase64(token.getBytes())) {
            return SecurityContextHolder.createEmptyContext();
        }

        CipherService cipherService = cipherAlgorithmService.getCipherService(sessionProperties.getCipherAlgorithmName());
        byte[] key = Base64.decode(sessionProperties.getCryptoKey());

        try {
            ByteSource byteSource = cipherService.decrypt(Base64.decode(token), key);
            String plaintext = new String(byteSource.obtainBytes(), Charset.defaultCharset());

            Map<String, Object> plaintextUserDetail = SystemException.convertSupplier(() -> CastUtils.getObjectMapper().readValue(plaintext, CastUtils.MAP_TYPE_REFERENCE));
            if (MapUtils.isEmpty(plaintextUserDetail)) {
                return null;
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[访问令牌获取数据]:获取当前用户信息，数据为:{}", plaintextUserDetail);
            }

            String type = plaintextUserDetail.getOrDefault(IdAuditEvent.TYPE_FIELD_NAME, StringUtils.EMPTY).toString();
            Object id = plaintextUserDetail.getOrDefault(IdEntity.ID_FIELD_NAME, StringUtils.EMPTY).toString();
            SecurityContext context = cacheManager.getSecurityContext(
                    type,
                    id,
                    authenticationProperties.getAccessToken().getCache()
            );
            if (Objects.isNull(context)) {
                return SecurityContextHolder.createEmptyContext();
            }

            if (!AuditAuthenticationToken.class.isAssignableFrom(context.getAuthentication().getClass())) {
                return SecurityContextHolder.createEmptyContext();
            }

            AuditAuthenticationToken authenticationToken = CastUtils.cast(context.getAuthentication());
            if (!AccessTokenDetails.class.isAssignableFrom(authenticationToken.getDetails().getClass())) {
                return SecurityContextHolder.createEmptyContext();
            }

            AccessTokenDetails accessTokenDetails = CastUtils.cast(authenticationToken.getDetails());
            if (Objects.isNull(accessTokenDetails)) {
                return SecurityContextHolder.createEmptyContext();
            }

            String existToken = accessTokenDetails.getToken().getValue();
            if (!Strings.CS.equals(existToken, token)) {
                return SecurityContextHolder.createEmptyContext();
            }

            SecurityPrincipal principal = authenticationToken.getSecurityPrincipal();
            if (principal instanceof MobileSecurityPrincipal) {
                MobileSecurityPrincipal mobileSecurityPrincipal = CastUtils.cast(principal);
                Object deviceIdentified = plaintextUserDetail.get(DeviceUtils.REQUEST_DEVICE_IDENTIFIED_PARAM_NAME);
                if (!Objects.equals(deviceIdentified, mobileSecurityPrincipal.getDeviceIdentified())) {
                    return SecurityContextHolder.createEmptyContext();
                }
            }

            return context;
        } catch (CryptoException e) {
            LOGGER.warn("通过密钥:{} 解密 token:{} 失败", sessionProperties.getCryptoKey(), token);
        }

        return SecurityContextHolder.createEmptyContext();
    }

    /**
     * 获取访问令牌的缓存键
     *
     * @param type 用户类型
     * @param deviceIdentified 设备标识
     * @return 访问令牌的缓存键
     */
    /*public String getAccessTokenKey(String type, Object deviceIdentified) {
        return authenticationProperties.getAccessToken()
                .getCache()
                .getName(type + CacheProperties.DEFAULT_SEPARATOR + deviceIdentified);
    }*/

    /**
     * 生成密文（使用默认密钥）
     *
     * @param token 审计认证令牌
     * @return Base64 编码的密文
     */
    /*public String generateCiphertext(AuditAuthenticationToken token) {
        return generateCiphertext(token, sessionProperties.getCryptoKey());
    }*/

    /**
     * 生成密文
     *
     * @param token 审计认证令牌
     * @param aesKey AES 密钥
     * @return Base64 编码的密文
     */
    /*public String generateCiphertext(AuditAuthenticationToken token, String aesKey) {
        Map<String, Object> json = new LinkedHashMap<>();

        json.put(NumberIdEntity.CREATION_TIME_FIELD_NAME, System.currentTimeMillis());
        json.put(IdAuditEvent.TYPE_FIELD_NAME, token.getType());
        json.put(IdEntity.ID_FIELD_NAME, token.getSecurityPrincipal().getId());

        if (token.getSecurityPrincipal() instanceof MobileSecurityPrincipal) {
            MobileSecurityPrincipal mobileSecurityPrincipal = CastUtils.cast(token.getPrincipal());
            json.put(DeviceUtils.REQUEST_DEVICE_IDENTIFIED_PARAM_NAME, mobileSecurityPrincipal.getDeviceIdentified());
        }

        String plaintext = SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(json));

        CipherService cipherService = cipherAlgorithmService.getCipherService(sessionProperties.getCipherAlgorithmName());
        byte[] key = Base64.decode(aesKey);
        ByteSource source = cipherService.encrypt(plaintext.getBytes(Charset.defaultCharset()), key);

        return source.getBase64();
    }*/

    /**
     * 保存安全上下文
     *
     * @param context 安全上下文
     * @param request HTTP 请求
     * @param response HTTP 响应
     */
    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        super.saveContext(context, request, response);
        AccessTokenContextRepository.super.saveContext(context, request, response);
    }

    @Override
    public CacheManager getCacheManager() {
        return cacheManager;
    }

    @Override
    public AuthenticationProperties getAuthenticationProperties() {
        return authenticationProperties;
    }

    @Override
    public SecurityContext getSecurityContext(
            String type,
            Object id
    ) {
        return cacheManager.getSecurityContext(type, id, authenticationProperties.getAccessToken().getCache());
    }
}
