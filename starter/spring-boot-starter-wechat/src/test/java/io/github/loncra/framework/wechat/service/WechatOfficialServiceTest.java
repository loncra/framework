package io.github.loncra.framework.wechat.service;

import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor;
import io.github.loncra.framework.wechat.MockWechatClientSupport;
import io.github.loncra.framework.wechat.OfficialProperties;
import io.github.loncra.framework.wechat.WechatProperties;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * 对公众号相关 HTTP 接口的 Mock 级单测，与 {@link WechatAppletServiceTest} 同思路。
 */
class WechatOfficialServiceTest {

    @Test
    void getUserListSuccess() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableOfficial service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("/cgi-bin/user/get")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"errcode\":0,\"total\":2,\"count\":1,\"data\":{\"openid\":[\"a\"]}}", MediaType.APPLICATION_JSON));

        Map<String, Object> r = service.getUserList(null);
        assertNotNull(r);
        assertEquals("0", r.get("errcode").toString());
        assertEquals(2, ((Number) r.get("total")).intValue());
        server.verify();
    }

    @Test
    void getUserListError() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableOfficial service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("/cgi-bin/user/get")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"errcode\":40014,\"errmsg\":\"invalid access_token\"}", MediaType.APPLICATION_JSON));

        assertThrows(ErrorCodeException.class, () -> service.getUserList(""));
        server.verify();
    }

    @Test
    void getOAuth2AccessTokenSuccess() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableOfficial service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("oauth2/access_token?")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        "{\"errcode\":0,\"access_token\":\"web-at\",\"expires_in\":7200,\"refresh_token\":\"r\",\"openid\":\"oabc\",\"scope\":\"snsapi_userinfo\"}",
                        MediaType.APPLICATION_JSON
                ));

        Map<String, Object> r = service.getOAuth2AccessToken("auth-code");
        assertEquals("oabc", r.get("openid").toString());
        assertEquals("web-at", r.get("access_token").toString());
        server.verify();
    }

    @Test
    void createQrcode() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableOfficial service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("/cgi-bin/qrcode/create?access_token=")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"errcode\":0,\"ticket\":\"t1\",\"expire_seconds\":600}", MediaType.APPLICATION_JSON));

        Map<String, Object> p = new LinkedHashMap<>();
        p.put("action_name", "QR_STR_SCENE");
        Map<String, Object> r = service.createQrcode(p);
        assertEquals("t1", r.get("ticket").toString());
        server.verify();
    }

    private static WechatTestableOfficial newService(RestTemplate client) {
        OfficialProperties off = new OfficialProperties();
        off.getAccessToken().setSecretId("wxOfficialId");
        off.getAccessToken().setSecretKey("offSecret");
        WechatProperties wp = new WechatProperties();
        ConcurrentInterceptor ci = MockWechatClientSupport.concurrentWithCachedAccessToken("off-test-token");
        return new WechatTestableOfficial(off, wp, ci, client);
    }

    private static final class WechatTestableOfficial extends WechatOfficialService {

        private final RestTemplate testClient;

        private WechatTestableOfficial(
                OfficialProperties officialProperties,
                WechatProperties wechatProperties,
                ConcurrentInterceptor concurrentInterceptor,
                RestTemplate testClient
        ) {
            super(officialProperties, wechatProperties, concurrentInterceptor);
            this.testClient = testClient;
        }

        @Override
        public RestTemplate getRestTemplate() {
            return testClient;
        }
    }
}
