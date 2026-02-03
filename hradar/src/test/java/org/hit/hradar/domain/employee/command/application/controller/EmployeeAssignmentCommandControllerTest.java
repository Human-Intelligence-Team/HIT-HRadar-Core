package org.hit.hradar.domain.employee.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeAssignmentResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeAssignmentRequest;
import org.hit.hradar.domain.employee.command.application.service.EmployeeUpdateApplicationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeAssignmentCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class EmployeeAssignmentCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeUpdateApplicationService employeeUpdateApplicationService;

    @MockBean
    private RequestUserContext requestUserContext;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("사원 인사이동 수정 성공")
    void updateAssignment_Success() throws Exception {
        // given
        Long empId = 1L;
        Long companyId = 1L;
        Long userId = 1L;
        UpdateEmployeeAssignmentRequest request = UpdateEmployeeAssignmentRequest.builder()
                .deptId(1L)
                .positionId(1L)
                .employeeNo("E001")
                .eventReason("昇進")
                .build();
        UpdateEmployeeAssignmentResponse response = UpdateEmployeeAssignmentResponse.builder()
                .empId(empId)
                .build();

        given(employeeUpdateApplicationService.updateAssignment(eq(companyId), eq(userId), eq(empId),
                any(UpdateEmployeeAssignmentRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(patch("/employees/{empId}/assignment", empId)
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Role", "admin")
                .header("X-Company-Id", String.valueOf(companyId))
                .header("X-Employee-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
