package io.github.loncra.framework.spring.security.oauth2.test;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.security.core.authentication.AccessTokenContextRepository;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringSecurityOauth2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Autowired
    private AuthorizationServerSettings authorizationServerSettings;

    @Test
    public void test() throws Exception {

        MockHttpSession session = new MockHttpSession();

        String bearerJson = mockMvc
                .perform(
                        post(authenticationProperties.getLoginProcessingUrl())
                                .param(authenticationProperties.getUsernameParamName(), "test")
                                .param(authenticationProperties.getPasswordParamName(), "123456")
                                .header(authenticationProperties.getTypeHeaderName(), "test")
                                .session(session)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":200}"))
                .andExpect(content().json("{\"data\":{\"type\":\"test\", \"principal\":{\"id\":1,\"username\":\"test\"}}}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestResult<Map<String, Object>> bearerResultJson = CastUtils.getObjectMapper().readValue(bearerJson, new TypeReference<>() {});
        Map<String, Object> details = CastUtils.cast(bearerResultJson.getData().get("details"));
        Map<String, Object> token = CastUtils.cast(details.get("token"));
        String bearerValue = token.get("value").toString();

        mockMvc
                .perform(get("/actuator/auditevents").header(AccessTokenContextRepository.DEFAULT_ACCESS_TOKEN_HEADER_NAME, bearerValue))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}}]}"));

        String codeJson = mockMvc
                .perform(
                        get(authorizationServerSettings.getAuthorizationEndpoint())
                                .queryParam(OAuth2ParameterNames.CLIENT_ID, "test")
                                .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, OAuth2ParameterNames.CODE)
                                .queryParam(OAuth2ParameterNames.SCOPE, "openid profile")
                                .queryParam(OAuth2ParameterNames.REDIRECT_URI, "http://www.domain.com")
                                .header(AccessTokenContextRepository.DEFAULT_ACCESS_TOKEN_HEADER_NAME, bearerValue)

                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":200}"))
                .andExpect(content().json("{\"data\":{\"authenticated\": true, \"clientId\": \"test\",\"scopes\": [\"openid\", \"profile\"]}}"))
                .andExpect(jsonPath("$.data.authorizationCode.tokenValue").value(not(emptyOrNullString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestResult<Map<String, Object>> codeResultJson = CastUtils.getObjectMapper().readValue(codeJson, new TypeReference<>() {});
        Map<String, Object> authorizationCodeMap = CastUtils.cast(codeResultJson.getData().get("authorizationCode"));
        String tokenValue = authorizationCodeMap.get("tokenValue").toString();

        String accessTokenJson = mockMvc
                .perform(
                        post(authorizationServerSettings.getTokenEndpoint())
                                .param(OAuth2ParameterNames.CLIENT_ID, "test")
                                .param(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue())
                                .param(OAuth2ParameterNames.CODE, tokenValue)
                                .param(OAuth2ParameterNames.CLIENT_SECRET, "123456")
                                .param(OAuth2ParameterNames.SCOPE, "openid profile")
                                .param(OAuth2ParameterNames.REDIRECT_URI, "http://www.domain.com")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":200}"))
                .andExpect(jsonPath("$.data.accessToken.tokenValue").value(not(emptyOrNullString())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        RestResult<Map<String, Object>> accessTokenResultJson = CastUtils.getObjectMapper().readValue(accessTokenJson, new TypeReference<>() {});
        Map<String, Object> accessTokenMap = CastUtils.cast(accessTokenResultJson.getData().get("accessToken"));
        String accessTokenValue = accessTokenMap.get("tokenValue").toString();

        mockMvc
                .perform(
                        get(authorizationServerSettings.getOidcUserInfoEndpoint())
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"status\":200}"))
                .andExpect(content().json("{\"data\":{\"type\":\"test\",\"principal\":{\"id\":\"1\",\"name\":\"1\"}}}"));

        mockMvc
                .perform(get("/actuator/auditevents").header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}},{\"principal\": \"test:1\",\"type\": \"AUTHENTICATION_SUCCESS\",\"data\": {\"details\": {\"requestDetails\": {\"jwt\": {\"subject\": \"test:1\",\"audience\": [\"test\"]},\"authorities\": [{\"authority\": \"SCOPE_openid\"}, {\"authority\": \"SCOPE_profile\"}]},\"metadata\": {},\"remember\": false}}}]}"));

    }
}
