package org.hit.hradar.domain.rolePermission.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.rolePermission.command.application.dto.CreatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.application.service.PermissionCommandService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PermissionCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class PermissionCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PermissionCommandService permissionCommandService;

    @MockBean
    private RequestUserContext requestUserContext;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("권한 생성 성공")
    void createPermission_Success() throws Exception {
        // given
        CreatePermissionRequest request = new CreatePermissionRequest(null, "PERM_TEST", "Test Permission", "/test",
                "Desc");
        given(permissionCommandService.createPermission(any(CreatePermissionRequest.class))).willReturn(1L);

        // when & then
        mockMvc.perform(post("/permissions")
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
