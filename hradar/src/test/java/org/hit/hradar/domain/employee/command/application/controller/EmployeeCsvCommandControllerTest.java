package org.hit.hradar.domain.employee.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvPreviewResponse;
import org.hit.hradar.domain.employee.command.application.dto.reponse.EmployeeCsvUploadResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.EmployeeCsvRegisterRequest;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCsvPreviewService;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCsvRegisterService;
import org.hit.hradar.domain.employee.command.application.service.EmployeeCsvTemplateService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeCsvCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class EmployeeCsvCommandControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private EmployeeCsvPreviewService employeeCsvPreviewService;

        @MockBean
        private EmployeeCsvRegisterService employeeCsvRegisterService;

        @MockBean
        private EmployeeCsvTemplateService employeeCsvTemplateService;

        @MockBean
        private RequestUserContext requestUserContext;

        @MockBean
        private JpaMetamodelMappingContext jpaMetamodelMappingContext;

        @Test
        @DisplayName("CSV 파일 미리보기 성공")
        void previewCsv_Success() throws Exception {
                // given
                Long companyId = 1L;
                MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv",
                                "name,email\ntest,test@example.com".getBytes());
                EmployeeCsvPreviewResponse response = EmployeeCsvPreviewResponse.builder()
                                .rows(Collections.emptyList())
                                .totalCount(0)
                                .validCount(0)
                                .invalidCount(0)
                                .build();

                given(employeeCsvPreviewService.preview(eq(companyId), any())).willReturn(response);

                // when & then
                mockMvc.perform(multipart("/employees/csv/preview")
                                .file(file)
                                .header("X-User-Id", "1")
                                .header("X-User-Role", "admin")
                                .header("X-Company-Id", String.valueOf(companyId))
                                .header("X-Employee-Id", "1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true));
        }

        @Test
        @DisplayName("사원 일괄 등록 실행 성공")
        void registerCsv_Success() throws Exception {
                // given
                Long companyId = 1L;
                List<EmployeeCsvRegisterRequest> requests = List.of(
                                EmployeeCsvRegisterRequest.builder()
                                                .name("홍길동")
                                                .email("hong@example.com")
                                                .phoneNo("010-1234-5678")
                                                .employeeNo("E001")
                                                .loginId("hong123")
                                                .password("password123!")
                                                .build());
                EmployeeCsvUploadResponse response = EmployeeCsvUploadResponse.builder()
                                .successCount(1)
                                .totalCount(1)
                                .build();

                given(employeeCsvRegisterService.register(eq(companyId), any())).willReturn(response);

                // when & then
                mockMvc.perform(post("/employees/csv/register")
                                .header("X-User-Id", "1")
                                .header("X-User-Role", "admin")
                                .header("X-Company-Id", String.valueOf(companyId))
                                .header("X-Employee-Id", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requests)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.data.successCount").value(1));
        }
}
