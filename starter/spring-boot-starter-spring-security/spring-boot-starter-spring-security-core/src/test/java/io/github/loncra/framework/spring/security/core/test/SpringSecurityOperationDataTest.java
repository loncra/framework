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
                                .param(authenticationProperties.getUsernameParamName(),"test")
                                .param(authenticationProperties.getPasswordParamName(),"123456")
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
                                                  "{\"principal\":\"test:1\",\"type\":\"OPERATION_DATA_AUDIT_tb_operation_data_INSERT\",\"data\":{\"details\":{\"remember\":false},\"operationDataTrace\":{\"target\":\"tb_operation_data\",\"type\":{\"name\":\"新增\",\"value\":\"INSERT\"},\"submitData\":{\"name\":\"test-operate-data\"},\"controllerAuditType\":\"CONTROLLER_AUDIT_OperateDataController_save\"}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"CONTROLLER_AUDIT_OperateDataController_save_SUCCESS\",\"data\":{\"header\":{\"Content-Type\":\"application/json;charset=UTF-8\"},\"body\":{\"name\":\"test-operate-data\"},\"details\":{\"remember\":false}}}]}"));

        content.setName("test-operate-data-update");

        mockMvc
                .perform(post("/operateData/save").content(CastUtils.getObjectMapper().writeValueAsString(content)).contentType(MediaType.APPLICATION_JSON_VALUE).session(session))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"ok\",\"status\":200}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"OPERATION_DATA_AUDIT_tb_operation_data_INSERT\",\"data\":{\"details\":{\"remember\":false},\"operationDataTrace\":{\"target\":\"tb_operation_data\",\"submitData\":{\"name\":\"test-operate-data\"},\"type\":{\"name\":\"新增\",\"value\":\"INSERT\"},\"controllerAuditType\":\"CONTROLLER_AUDIT_OperateDataController_save\"}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"CONTROLLER_AUDIT_OperateDataController_save_SUCCESS\",\"data\":{\"header\":{\"Content-Type\":\"application/json;charset=UTF-8\"},\"body\":{\"name\":\"test-operate-data\"},\"details\":{\"remember\":false}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"OPERATION_DATA_AUDIT_tb_operation_data_UPDATE\",\"data\":{\"details\":{\"remember\":false},\"operationDataTrace\":{\"target\":\"tb_operation_data\",\"submitData\":{\"name\":\"test-operate-data-update\"},\"type\":{\"name\":\"更新\",\"value\":\"UPDATE\"},\"controllerAuditType\":\"CONTROLLER_AUDIT_OperateDataController_save\"}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"CONTROLLER_AUDIT_OperateDataController_save_SUCCESS\",\"data\":{\"header\":{\"Content-Type\":\"application/json;charset=UTF-8\"},\"body\":{\"name\":\"test-operate-data-update\"},\"details\":{\"remember\":false}}}]}"));

        mockMvc
                .perform(post("/operateData/delete").param("ids", content.getId().toString()).session(session))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"删除 1 记录成功\",\"status\":200}"));

        mockMvc
                .perform(get("/actuator/auditevents"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"events\":[{\"principal\":\"test:1\",\"type\":\"AUTHENTICATION_SUCCESS\",\"data\":{\"details\":{\"remember\":false}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"OPERATION_DATA_AUDIT_tb_operation_data_INSERT\",\"data\":{\"details\":{\"remember\":false},\"operationDataTrace\":{\"target\":\"tb_operation_data\",\"submitData\":{\"name\":\"test-operate-data\"},\"type\":{\"name\":\"新增\",\"value\":\"INSERT\"},\"controllerAuditType\":\"CONTROLLER_AUDIT_OperateDataController_save\"}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"CONTROLLER_AUDIT_OperateDataController_save_SUCCESS\",\"data\":{\"header\":{\"Content-Type\":\"application/json;charset=UTF-8\"},\"body\":{\"name\":\"test-operate-data\"},\"details\":{\"remember\":false}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"OPERATION_DATA_AUDIT_tb_operation_data_UPDATE\",\"data\":{\"details\":{\"remember\":false},\"operationDataTrace\":{\"target\":\"tb_operation_data\",\"submitData\":{\"name\":\"test-operate-data-update\"},\"type\":{\"name\":\"更新\",\"value\":\"UPDATE\"},\"controllerAuditType\":\"CONTROLLER_AUDIT_OperateDataController_save\"}}}," +
                                                  "{\"principal\":\"test:1\",\"type\":\"CONTROLLER_AUDIT_OperateDataController_save_SUCCESS\",\"data\":{\"header\":{\"Content-Type\":\"application/json;charset=UTF-8\"},\"body\":{\"name\":\"test-operate-data-update\"},\"details\":{\"remember\":false}}},{\"principal\":\"test:1\",\"type\":\"CONTROLLER_AUDIT_OperateDataController_delete_SUCCESS\",\"data\":{\"header\":{},\"parameter\":{\"ids\":[\""+content.getId()+"\"]},\"details\":{\"remember\":false}}}]}"));

    }

}
