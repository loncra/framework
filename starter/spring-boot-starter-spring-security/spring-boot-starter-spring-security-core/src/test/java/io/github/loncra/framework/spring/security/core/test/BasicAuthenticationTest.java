package io.github.loncra.framework.spring.security.core.test;

import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.authentication.service.feign.FeignAuthenticationConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BasicAuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Test
    public void test() throws Exception {

        Optional<SecurityProperties.User> optionalUser = authenticationProperties.getUsers().stream().filter(u -> u.getName().equals("feign")).findFirst();
        Assertions.assertTrue(optionalUser.isPresent());
        String password = FeignAuthenticationConfiguration.encodeUserProperties(optionalUser.get());

        mockMvc
                .perform(get("/operate/feignBasicAuthentication").header(HttpHeaders.AUTHORIZATION, BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC + StringUtils.SPACE + password))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"feignBasicAuthentication\"}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[]}"));

        mockMvc
                .perform(get("/operate/simpleBasicAuthentication").header(HttpHeaders.AUTHORIZATION, BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC + StringUtils.SPACE + password))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"message\":\"Access Denied\"}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[]}"));

        optionalUser = authenticationProperties.getUsers().stream().filter(u -> u.getName().equals("simple")).findFirst();
        Assertions.assertTrue(optionalUser.isPresent());
        password = FeignAuthenticationConfiguration.encodeUserProperties(optionalUser.get());

        mockMvc
                .perform(get("/operate/simpleBasicAuthentication").header(HttpHeaders.AUTHORIZATION, BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC + StringUtils.SPACE + password))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"simpleBasicAuthentication\"}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"simple\",\"type\":\"AUTHENTICATION_SUCCESS\"}]}"));

        mockMvc
                .perform(get("/operate/feignBasicAuthentication").header(HttpHeaders.AUTHORIZATION, BasicAuthenticationConverter.AUTHENTICATION_SCHEME_BASIC + StringUtils.SPACE + password))
                .andExpect(status().is4xxClientError())
                .andExpect(content().json("{\"message\":\"Access Denied\"}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"simple\",\"type\":\"AUTHENTICATION_SUCCESS\"},{\"principal\":\"simple\",\"type\":\"AUTHENTICATION_SUCCESS\"}]}"));
    }
}
