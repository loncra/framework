package io.github.loncra.framework.wechat.service;

import io.github.loncra.framework.commons.exception.ErrorCodeException;
import io.github.loncra.framework.wechat.AppletProperties;
import io.github.loncra.framework.wechat.MockWechatClientSupport;
import io.github.loncra.framework.wechat.WechatProperties;
import io.github.loncra.framework.wechat.domain.WechatUserDetails;
import io.github.loncra.framework.wechat.domain.metadata.applet.PhoneInfoMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * 对小程序 HTTP 接口的 Mock 级单测：不访问真实微信，不依赖真 appid/secret（后续可另做集成/契约测试）。
 */
class WechatAppletServiceTest {

    @Test
    void loginSuccess() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("jscode2session")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess("{\"errcode\":0,\"session_key\":\"sk-1\",\"openid\":\"oid-1\"}", MediaType.APPLICATION_JSON)
                );

        WechatUserDetails u = service.login("js-code-xyz");
        assertEquals("oid-1", u.getOpenId());
        assertEquals("sk-1", u.getSessionKey());
        server.verify();
    }

    @Test
    void loginWechatErrorThrows() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("jscode2session")))
                .andRespond(withSuccess("{\"errcode\":40029,\"errmsg\":\"invalid code\"}", MediaType.APPLICATION_JSON));

        assertThrows(ErrorCodeException.class, () -> service.login("bad"));
        server.verify();
    }

    @Test
    void getPhoneNumberSuccess() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("getuserphonenumber?access_token=")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{\"errcode\":0,\"phone_info\":{\"phoneNumber\":\"86138\",\"purePhoneNumber\":\"138\",\"countryCode\":\"86\"}}",
                        MediaType.APPLICATION_JSON
                ));

        PhoneInfoMetadata phone = service.getPhoneNumber("p-code");
        assertEquals("86138", phone.getPhoneNumber());
        assertEquals("138", phone.getPurePhoneNumber());
        assertEquals("86", phone.getCountryCode());
        server.verify();
    }

    @Test
    void createAppletQrcodeReturnsPngBytes() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        byte[] png = new byte[]{-119, 80, 78, 71, 0x0d, 0x0a};
        server.expect(ExpectedCount.once(), requestTo(containsString("getwxacodeunlimit?access_token=")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(png, MediaType.IMAGE_PNG));

        Map<String, Object> param = new LinkedHashMap<>();
        param.put("page", "pages/index");
        byte[] out = service.createAppletQrcode(param);
        assertArrayEquals(png, out);
        server.verify();
    }

    @Test
    void createAppletQrcodeJsonErrorThrows() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("getwxacodeunlimit?access_token=")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withSuccess("{\"errcode\":40001,\"errmsg\":\"invalid credential\"}", MediaType.APPLICATION_JSON)
                );

        assertThrows(
                ErrorCodeException.class,
                () -> service.createAppletQrcode(new LinkedHashMap<>())
        );
        server.verify();
    }

    @Test
    void sendSubscribeMessageSuccess() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("message/subscribe/send?access_token=")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"errcode\":0,\"errmsg\":\"ok\"}", MediaType.APPLICATION_JSON));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("touser", "openid");
        Map<String, Object> r = service.sendSubscribeMessage(body);
        assertNotNull(r);
        assertEquals("0", r.get("errcode").toString());
        server.verify();
    }

    @Test
    void generateWxaUrlScheme() {
        RestTemplate client = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(client).build();
        WechatTestableApplet service = newService(client);

        server.expect(ExpectedCount.once(), requestTo(containsString("wxa/generatescheme?access_token=")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"errcode\":0,\"openlink\":\"weixin://\"}", MediaType.APPLICATION_JSON));

        Map<String, Object> r = service.generateWxaUrlScheme(new LinkedHashMap<>());
        assertEquals("weixin://", r.get("openlink").toString());
        server.verify();
    }

    private static WechatTestableApplet newService(RestTemplate client) {
        AppletProperties ap = new AppletProperties();
        ap.getAccessToken().setSecretId("wxTestAppid");
        ap.getAccessToken().setSecretKey("testSecret");
        WechatProperties wp = new WechatProperties();
        return new WechatTestableApplet(
                ap, wp, MockWechatClientSupport.concurrentWithCachedAccessToken("test-access-token"), client
        );
    }

    private static final class WechatTestableApplet extends WechatAppletService {

        private final RestTemplate testClient;

        private WechatTestableApplet(
                AppletProperties appletProperties,
                WechatProperties wechatProperties,
                io.github.loncra.framework.idempotent.advisor.concurrent.ConcurrentInterceptor concurrent,
                RestTemplate testClient
        ) {
            super(appletProperties, wechatProperties, concurrent);
            this.testClient = testClient;
        }

        @Override
        public RestTemplate getRestTemplate() {
            return testClient;
        }
    }
}
