package org.hit.hradar.domain.department.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.department.command.application.dto.CreateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.dto.UpdateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.service.DepartmentCommandService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class DepartmentCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepartmentCommandService departmentCommandService;

    @MockBean
    private RequestUserContext requestUserContext;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("부서 생성 성공")
    void create_Success() throws Exception {
        // given
        Long companyId = 1L;
        CreateDepartmentRequest request = new CreateDepartmentRequest("New Dept", null, null, "02-123-4567");

        given(departmentCommandService.createDepartment(any(CreateDepartmentRequest.class), eq(companyId)))
                .willReturn(1L);

        // when & then
        mockMvc.perform(post("/departments")
                .header("X-User-Id", "1")
                .header("X-User-Role", "admin")
                .header("X-Company-Id", String.valueOf(companyId))
                .header("X-Employee-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("부서 정보 수정 성공")
    void update_Success() throws Exception {
        // given
        Long deptId = 1L;
        Long companyId = 1L;
        UpdateDepartmentRequest request = new UpdateDepartmentRequest("Updated Dept", null, null, "02-111-2222");

        // when & then
        mockMvc.perform(patch("/departments/{deptId}", deptId)
                .header("X-User-Id", "1")
                .header("X-User-Role", "admin")
                .header("X-Company-Id", String.valueOf(companyId))
                .header("X-Employee-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("부서 삭제 성공")
    void delete_Success() throws Exception {
        // given
        Long deptId = 1L;
        Long companyId = 1L;

        // when & then
        mockMvc.perform(delete("/departments/{deptId}", deptId)
                .header("X-User-Id", "1")
                .header("X-User-Role", "admin")
                .header("X-Company-Id", String.valueOf(companyId))
                .header("X-Employee-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
