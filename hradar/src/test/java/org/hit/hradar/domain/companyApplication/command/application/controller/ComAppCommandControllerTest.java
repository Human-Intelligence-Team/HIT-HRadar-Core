package org.hit.hradar.domain.companyApplication.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.companyApplication.command.application.service.ComAppApprovalService;
import org.hit.hradar.domain.companyApplication.command.application.service.ComAppCommandService;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComAppCommandController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class ComAppCommandControllerTest {

        @MockBean
        private ComAppCommandService comAppCommandService;

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ComAppApprovalService comAppApprovalService;

        @MockBean
        private RequestUserContext requestUserContext;

        @MockBean
        private JpaMetamodelMappingContext jpaMetamodelMappingContext;

        @Test
        @DisplayName("신청 승인 및 계정 생성 성공")
        void approve_Success() throws Exception {
                // given
                Long applicationId = 1L;
                Long userId = 1L;

                // when & then
                mockMvc.perform(post("/company-applications/{applicationId}/approve", applicationId)
                                .header("X-User-Id", String.valueOf(userId))
                                .header("X-User-Role", "admin")
                                .header("X-Company-Id", "1")
                                .header("X-Employee-Id", "1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true));
        }
}
