package org.hit.hradar.domain.rolePermission.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.rolePermission.command.application.dto.CreateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.application.service.RoleCommandService;
import org.hit.hradar.global.aop.AuthUserArgumentResolver;
import org.hit.hradar.global.config.WebConfig;
import org.hit.hradar.global.context.RequestUserContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class RoleCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleCommandService roleCommandService;

    @MockBean
    private RequestUserContext requestUserContext;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("역할 생성 성공")
    void createRole_Success() throws Exception {
        // given
        CreateRoleRequest request = new CreateRoleRequest("ROLE_TEST", Collections.emptyList());
        given(roleCommandService.createCustomRole(any(), any(CreateRoleRequest.class))).willReturn(1L);

        // when & then
        mockMvc.perform(post("/roles")
                .header("X-User-Id", "1")
                .header("X-User-Role", "admin")
                .header("X-Company-Id", "1")
                .header("X-Employee-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(1L));
    }
}
