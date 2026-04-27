package io.github.loncra.framework.wechat.service;

import io.github.loncra.framework.commons.domain.AccessToken;
import io.github.loncra.framework.commons.domain.metadata.RefreshAccessTokenMetadata;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.wechat.OfficialProperties;
import io.github.loncra.framework.wechat.WechatProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 微信公众号（服务号/订阅号同一套开放平台接口中的「公众平台」能力）。access_token 与小程序隔离：使用 {@link OfficialProperties#getAccessToken()} 的独立缓存名。
 */
public class WechatOfficialService extends AbstractWechatBasicService implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatOfficialService.class);

    private final OfficialProperties officialProperties;

    public WechatOfficialService(
            OfficialProperties officialProperties,
            WechatProperties wechatProperties,
            ConcurrentInterceptor concurrentInterceptor
    ) {
        super(wechatProperties, concurrentInterceptor);
        this.officialProperties = officialProperties;
    }

    @Override
    protected RefreshAccessTokenMetadata getRefreshAccessTokenMetadata() {
        return officialProperties.getAccessToken();
    }

    public OfficialProperties getOfficialConfig() {
        return officialProperties;
    }

    @Override
    public void afterPropertiesSet() {
        if (StringUtils.isEmpty(officialProperties.getAccessToken().getSecretId())
                || StringUtils.isEmpty(officialProperties.getAccessToken().getSecretKey())) {
            LOGGER.info("[微信公众号] 未配置 appid/secret，跳过启动时拉取 access_token");
            return;
        }
        try {
            AccessToken accessToken = refreshAccessToken();
            LOGGER.info("[微信公众号] 当前 token 为: {}, 在: {} 后超时", accessToken.getValue(), accessToken.getExpiresInDateTime());
        }
        catch (Exception e) {
            LOGGER.error("[微信公众号] 获取 access_token 失败", e);
        }
    }

    /**
     * 拉取关注者 openid 列表
     *
     * @param nextOpenid 首次可传空或 {@code null}，非首次传上次返回的 next_openid
     * @return 平台返回体（含 total、count、data、next_openid 等）
     * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/User_Management/Getting_a_User_List.html">doc</a>
     */
    public Map<String, Object> getUserList(String nextOpenid) {
        AccessToken token = getAccessTokenIfCacheNull();
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.weixin.qq.com/cgi-bin/user/get")
                .queryParam("access_token", token.getValue())
                .queryParam("next_openid", nextOpenid == null ? StringUtils.EMPTY : nextOpenid)
                .toUriString();

        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result)) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return result.getBody();
    }

    /**
     * 创建带参数二维码（场景值）。临时/永久、整型/字符串等按微信文档构造 {@code param} 的 action_name、action_info。
     */
    public Map<String, Object> createQrcode(Map<String, Object> param) {
        AccessToken token = getAccessTokenIfCacheNull();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token.getValue();
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(param, headers),
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    private static Map<String, Object> createEmptyErrBody() {
        return new LinkedHashMap<>();
    }

    /**
     * 创建/覆盖当前菜单
     */
    public Map<String, Object> createMenu(Map<String, Object> menu) {
        AccessToken token = getAccessTokenIfCacheNull();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token.getValue();
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(menu, headers),
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    public Map<String, Object> getMenu() {
        AccessToken token = getAccessTokenIfCacheNull();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + token.getValue();
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    public Map<String, Object> deleteMenu() {
        AccessToken token = getAccessTokenIfCacheNull();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token.getValue();
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    /**
     * 发送模板消息（touser、template_id、data 等，见平台文档；行业模板/类目模板以当前后台为准）
     */
    public Map<String, Object> sendTemplateMessage(Map<String, Object> body) {
        AccessToken token = getAccessTokenIfCacheNull();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token.getValue();
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    /**
     * 网页授权：用 code 换取 {@code openid}、scope 为 snsapi_userinfo 时的 access_token
     */
    public Map<String, Object> getOAuth2AccessToken(String code) {
        String appid = officialProperties.getAccessToken().getSecretId();
        String secret = officialProperties.getAccessToken().getSecretKey();
        String url = MessageFormat.format(
                "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code",
                appid, secret, code
        );
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    public Map<String, Object> getSnsUserInfo(String oauthAccessToken, String openid, String lang) {
        String l = StringUtils.isEmpty(lang) ? "zh_CN" : lang;
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + urlEncode(oauthAccessToken) + "&openid=" + urlEncode(openid) + "&lang=" + l;
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }

    private static String urlEncode(String s) {
        if (s == null) {
            return StringUtils.EMPTY;
        }
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    /**
     * 客服消息-发文本（可扩展为其它 msgtype，此处提供最常见文本）
     */
    public Map<String, Object> sendKfText(String openid, String content) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("touser", openid);
        body.put("msgtype", "text");
        Map<String, String> text = new LinkedHashMap<>();
        text.put("content", content);
        body.put("text", text);

        AccessToken token = getAccessTokenIfCacheNull();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token.getValue();
        ResponseEntity<Map<String, Object>> result = getRestTemplate().exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<>() {
                }
        );
        if (isSuccess(result) && result.getBody() != null) {
            return result.getBody();
        }
        throwSystemExceptionIfError(result.getBody() != null ? result.getBody() : createEmptyErrBody());
        return new LinkedHashMap<>();
    }
}
