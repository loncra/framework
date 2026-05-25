package io.github.loncra.framework.spring.security.core.test;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.RestResult;
import io.github.loncra.framework.spring.security.core.authentication.config.AuthenticationProperties;
import io.github.loncra.framework.spring.security.core.test.entity.OperationDataEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SpringSecurityOperationDataTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Test
    public void testOperateData() throws Exception {

        MockHttpSession session = new MockHttpSession();

        mockMvc
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
                .andExpect(content().json("{\"data\":{\"type\":\"test\", \"principal\":{\"id\":1,\"username\":\"test\"}}}"));

        OperationDataEntity content = new OperationDataEntity();
        content.setName("test-operate-data");

        RestResult<Integer> restResult = CastUtils.getObjectMapper().readValue(
                mockMvc
                        .perform(post("/operateData/save").content(CastUtils.getObjectMapper().writeValueAsString(content)).contentType(MediaType.APPLICATION_JSON_VALUE).session(session))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"message\":\"ok\",\"status\":200}"))
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<>() {
                }
        );
        content.setId(restResult.getData());

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"controllerAudit\",\"data\":{\"details\":{\"remember\":false},\"metadata\":{\"id\":\"save\",\"name\":\"OperateDataController_save\",\"executeStatus\":\"Success\",\"url\":\"http://localhost/operateData/save\",\"httpMethod\":\"POST\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"operationDataTraceAudit\",\"data\":{\"metadata\":{\"id\":\"save\",\"headers\":{\"Content-Type\":[\"application/json;charset=UTF-8\"]}},\"details\":{\"remember\":false},\"operationTrace\": {\"target\":\"tb_operation_data\",\"data\":{\"name\":\"test-operate-data\",\"principal\":\"test:1\"},\"type\":\"INSERT\"}}}]}"));

        content.setName("test-operate-data-update");

        mockMvc
                .perform(post("/operateData/save").content(CastUtils.getObjectMapper().writeValueAsString(content)).contentType(MediaType.APPLICATION_JSON_VALUE).session(session))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"ok\",\"status\":200}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"controllerAudit\",\"data\":{\"details\":{\"remember\":false},\"metadata\":{\"id\":\"save\",\"name\":\"OperateDataController_save\",\"executeStatus\":\"Success\",\"url\":\"http://localhost/operateData/save\",\"httpMethod\":\"POST\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"operationDataTraceAudit\",\"data\":{\"metadata\":{\"id\":\"save\",\"headers\":{\"Content-Type\":[\"application/json;charset=UTF-8\"]}},\"details\":{\"remember\":false},\"operationTrace\": {\"target\":\"tb_operation_data\",\"data\":{\"name\":\"test-operate-data\",\"principal\":\"test:1\"}}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"controllerAudit\",\"data\":{\"details\":{\"remember\":false},\"metadata\":{\"id\":\"save\",\"name\":\"OperateDataController_save\",\"executeStatus\":\"Success\",\"url\":\"http://localhost/operateData/save\",\"httpMethod\":\"POST\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"operationDataTraceAudit\",\"data\":{\"metadata\":{\"id\":\"save\",\"headers\":{\"Content-Type\":[\"application/json;charset=UTF-8\"]}},\"details\":{\"remember\":false},\"operationTrace\": {\"target\":\"tb_operation_data\",\"data\":{\"name\":\"test-operate-data-update\",\"principal\":\"test:1\"}, \"type\":\"UPDATE\"}}}]}"));

        mockMvc
                .perform(post("/operateData/delete").param("ids", content.getId().toString()).session(session))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"删除 1 记录成功\",\"status\":200}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"controllerAudit\",\"data\":{\"details\":{\"remember\":false},\"metadata\":{\"id\":\"save\",\"name\":\"OperateDataController_save\",\"executeStatus\":\"Success\",\"url\":\"http://localhost/operateData/save\",\"httpMethod\":\"POST\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"operationDataTraceAudit\",\"data\":{\"metadata\":{\"id\":\"save\",\"headers\":{\"Content-Type\":[\"application/json;charset=UTF-8\"]}},\"details\":{\"remember\":false},\"operationTrace\": {\"target\":\"tb_operation_data\",\"data\":{\"name\":\"test-operate-data\",\"principal\":\"test:1\"}}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"controllerAudit\",\"data\":{\"details\":{\"remember\":false},\"metadata\":{\"id\":\"save\",\"name\":\"OperateDataController_save\",\"executeStatus\":\"Success\",\"url\":\"http://localhost/operateData/save\",\"httpMethod\":\"POST\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"operationDataTraceAudit\",\"data\":{\"metadata\":{\"id\":\"save\",\"headers\":{\"Content-Type\":[\"application/json;charset=UTF-8\"]}},\"details\":{\"remember\":false},\"operationTrace\": {\"target\":\"tb_operation_data\",\"data\":{\"name\":\"test-operate-data-update\",\"principal\":\"test:1\"}, \"type\":\"UPDATE\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"controllerAudit\",\"data\":{\"details\":{\"remember\":false},\"metadata\":{\"id\":\"delete\",\"name\":\"OperateDataController_delete\",\"executeStatus\":\"Success\",\"url\":\"http://localhost/operateData/delete\",\"httpMethod\":\"POST\"}}}," +
                        "{\"principal\":\"test:1\",\"type\":\"operationDataTraceAudit\",\"data\":{\"metadata\":{\"id\":\"delete\",\"headers\":{},\"parameters\":{\"ids\":[\"" + content.getId() + "\"]}},\"details\":{\"remember\":false},\"operationTrace\": {\"target\":\"tb_operation_data\",\"data\":{\"coll\":[" + content.getId() + "]}, \"type\":\"DELETE\"}}}]}"));
    }

}
