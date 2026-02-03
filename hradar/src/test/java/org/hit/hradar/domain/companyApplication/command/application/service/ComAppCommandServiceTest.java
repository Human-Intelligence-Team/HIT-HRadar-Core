package org.hit.hradar.domain.companyApplication.command.application.service;

import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppRequest;
import org.hit.hradar.domain.companyApplication.command.application.dto.CreateComAppResponse;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;
import org.hit.hradar.domain.companyApplication.command.domain.repository.ComAppRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ComAppCommandServiceTest {

    @InjectMocks
    private ComAppCommandService comAppCommandService;

    @Mock
    private ComAppRepository comAppRepository;

    @Test
    @DisplayName("회사 가입 신청서 생성 성공")
    void create_Success() {
        // given
        CreateComAppRequest req = CreateComAppRequest.builder()
                .companyName("Test Company")
                .bizNo("123-45-67890")
                .comTel("02-1234-5678")
                .address("Test Address")
                .name("Applicant")
                .email("test@example.com")
                .loginId("admin01")
                .build();

        CompanyApplication savedApp = CompanyApplication.builder()
                .appId(1L)
                .companyName("Test Company")
                .status(CompanyApplicationStatus.SUBMITTED)
                .build();

        given(comAppRepository.save(any(CompanyApplication.class))).willReturn(savedApp);

        // when
        CreateComAppResponse response = comAppCommandService.create(req);

        // then
        assertThat(response.getApplicationId()).isEqualTo(1L);
        assertThat(response.getCompanyApplicationStatus()).isEqualTo("SUBMITTED");
        verify(comAppRepository).save(any(CompanyApplication.class));
    }
}
