package org.hit.hradar.domain.employee.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateEmployeeWithAccountResponse;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeProfileResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateEmployeeWithAccountRequest;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeProfileRequest;
import org.hit.hradar.domain.employee.command.application.service.EmployeeAccountCommandService;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCommandService;
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

@WebMvcTest(EmployeeProfileCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class EmployeeProfileCommandControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private EmployeeCommandService employeeCommandService;

        @MockBean
        private EmployeeAccountCommandService employeeAccountCommandService;

        @MockBean
        private RequestUserContext requestUserContext;

        @MockBean
        private JpaMetamodelMappingContext jpaMetamodelMappingContext;

        @Test
        @DisplayName("사원 생성 및 계정 발급 성공")
        void createEmployee_Success() throws Exception {
                // given
                Long companyId = 1L;
                CreateEmployeeWithAccountRequest request = CreateEmployeeWithAccountRequest.builder()
                                .employeeNo("E001")
                                .name("Test User")
                                .loginId("testuser")
                                .email("test@example.com")
                                .password("password123!")
                                .build();
                CreateEmployeeWithAccountResponse response = new CreateEmployeeWithAccountResponse(1L, 1L, "E001",
                                "test@example.com");

                given(employeeAccountCommandService.createEmployeeWithAccount(eq(companyId),
                                any(CreateEmployeeWithAccountRequest.class)))
                                .willReturn(response);

                // when & then
                mockMvc.perform(post("/employees")
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
        @DisplayName("사원 프로필 수정 성공")
        void updateProfile_Success() throws Exception {
                // given
                Long empId = 1L;
                Long companyId = 1L;
                UpdateEmployeeProfileRequest request = UpdateEmployeeProfileRequest.builder()
                                .name("Updated Name")
                                .build();
                UpdateEmployeeProfileResponse response = new UpdateEmployeeProfileResponse();

                given(employeeCommandService.updateProfile(eq(companyId), eq(empId),
                                any(UpdateEmployeeProfileRequest.class)))
                                .willReturn(response);

                // when & then
                mockMvc.perform(patch("/employees/{empId}/profile", empId)
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
        @DisplayName("사원 삭제 성공")
        void deleteEmployee_Success() throws Exception {
                // given
                Long empId = 1L;
                Long companyId = 1L;

                // when & then
                mockMvc.perform(delete("/employees/{empId}", empId)
                                .header("X-User-Id", "1")
                                .header("X-User-Role", "admin")
                                .header("X-Company-Id", String.valueOf(companyId))
                                .header("X-Employee-Id", "1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true));
        }
}
