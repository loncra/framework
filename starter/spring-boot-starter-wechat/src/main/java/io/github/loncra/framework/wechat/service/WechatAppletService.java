package io.github.loncra.framework.wechat.service;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.domain.AccessToken;
import io.github.loncra.framework.commons.domain.metadata.RefreshAccessTokenMetadata;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.wechat.AppletProperties;
import io.github.loncra.framework.wechat.WechatProperties;
import io.github.loncra.framework.wechat.domain.WechatUserDetails;
import io.github.loncra.framework.wechat.domain.metadata.applet.PhoneInfoMetadata;
import io.github.loncra.framework.wechat.domain.metadata.applet.SimpleWechatUserDetailsMetadata;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 微信小程序服务
 *
 * @author maurice.chen
 */
public class WechatAppletService extends AbstractWechatBasicService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatAppletService.class);

    /**
     * 小程序配置属性
     */
    private final AppletProperties appletProperties;

    /**
     * 构造函数
     *
     * @param appletProperties 小程序配置属性
     * @param wechatProperties 微信配置属性
     * @param concurrentInterceptor 并发拦截器
     */
    public WechatAppletService(AppletProperties appletProperties,
                               WechatProperties wechatProperties,
                               ConcurrentInterceptor concurrentInterceptor) {
        super(wechatProperties, concurrentInterceptor);
        this.appletProperties = appletProperties;
    }

    /**
     * 获取刷新访问令牌的元数据
     *
     * @return 刷新访问令牌的元数据
     */
    @Override
    protected RefreshAccessTokenMetadata getRefreshAccessTokenMetadata() {
        return appletProperties.getAccessToken();
    }

    /**
     * 初始化方法，在 Bean 属性设置后调用
     */
    @Override
    public void afterPropertiesSet() {
        try {
            AccessToken accessToken = refreshAccessToken();
            LOGGER.info("[微信小程序] 当前 token 为: {}, 在: {} 后超时", accessToken.getValue(), accessToken.getExpiresInDateTime());
        } catch (Exception e) {
            LOGGER.error("[微信小程序] 获取访问 token 失败", e);
        }
    }

    /**
     * 获取小程序配置
     *
     * @return 小程序配置属性
     */
    public AppletProperties getAppletConfig() {
        return appletProperties;
    }

    /**
     * 获取小程序手机号码
     *
     * @param code 手机号获取凭证
     * @return 微信手机号码元数据信息
     */
    public PhoneInfoMetadata getPhoneNumber(String code) {
        AccessToken token = getAccessTokenIfCacheNull();

        Map<String, String> body = new LinkedHashMap<>();
        body.put("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + token.getValue(),
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<>() {
                }
        );

        if (isSuccess(result) && Objects.requireNonNull(result.getBody()).containsKey("phone_info")) {
            return new PhoneInfoMetadata(CastUtils.cast(result.getBody().get("phone_info")));
        } else {
            throwSystemExceptionIfError(result.getBody());
        }

        return null;
    }

    /**
     * 小程序登录
     *
     * @param code 登录凭证
     * @return 微信用户详情
     */
    public WechatUserDetails login(String code) {
        String url = MessageFormat.format("https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code", getAppletConfig().getAccessToken().getSecretId(), getAppletConfig().getAccessToken().getSecretKey(), code);
        ResponseEntity<String> result = getRestTemplate().getForEntity(url, String.class);

        String bodyString = Objects.toString(result.getBody(), StringUtils.EMPTY);
        if (StringUtils.isEmpty(bodyString)) {
            throwSystemExceptionIfError(new LinkedHashMap<>());
        }

        Map<String, Object> body = SystemException.convertSupplier(() -> CastUtils.getObjectMapper().readValue(result.getBody(), CastUtils.MAP_TYPE_REFERENCE));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("微信小程序登陆，响应结果为:{}", body);
        }

        if (isSuccess(new ResponseEntity<>(body, result.getHeaders(),result.getStatusCode()))) {
            return SimpleWechatUserDetailsMetadata.of(body);
        } else {
            throwSystemExceptionIfError(body);
        }

        return null;
    }

    /**
     * 创建小程序二维码
     *
     * @param param 参数信息
     */
    public byte[] createAppletQrcode(Map<String, Object> param) {
        AccessToken token = getAccessTokenIfCacheNull();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[创建小程序二维码]:提交参数信息为:{}", param);
        }

        ResponseEntity<byte[]> result = getRestTemplate().exchange(
                "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token.getValue(),
                HttpMethod.POST,
                new HttpEntity<>(param, headers),
                new ParameterizedTypeReference<>() {
                }
        );

        return result.getBody();
    }
}
