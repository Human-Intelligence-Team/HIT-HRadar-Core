package org.hit.hradar.domain.user.command.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hit.hradar.domain.user.command.application.dto.request.UpdateUserAccountRequest;
import org.hit.hradar.domain.user.command.application.service.UserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserUpdateController.class)
@Import({ WebConfig.class, AuthUserArgumentResolver.class })
class UserUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private RequestUserContext requestUserContext;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("계정 정보 업데이트 성공")
    void updateUserAccount_Success() throws Exception {
        // given
        Long accId = 1L;
        UpdateUserAccountRequest request = new UpdateUserAccountRequest("newadmin", "Administrator",
                "admin@example.com");

        // when & then
        mockMvc.perform(patch("/users/{accId}", accId)
                .header("X-User-Id", "1")
                .header("X-User-Role", "admin")
                .header("X-Company-Id", "1")
                .header("X-Employee-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
