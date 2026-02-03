package org.hit.hradar.domain.company.command.application.service;

import org.hit.hradar.domain.company.command.application.dto.request.UpdateCompanyRequest;
import org.hit.hradar.domain.company.command.application.dto.response.UpdateCompanyResponse;
import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.rolePermission.command.application.service.DefaultRoleCommandService;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CompanyCommandServiceTest {

    @InjectMocks
    private CompanyCommandService companyCommandService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DefaultRoleCommandService defaultRoleCommandService;

    @Test
    @DisplayName("회사 정보 수정 성공")
    void updateCompany_Success() {
        // given
        Long comId = 1L;
        Long companyId = 1L;
        UpdateCompanyRequest request = UpdateCompanyRequest.builder()
                .comName("Updated Name")
                .ceoName("New CEO")
                .build();

        Company company = Company.builder()
                .companyId(comId)
                .companyName("Old Name")
                .isDeleted('N')
                .build();

        given(companyRepository.findById(comId)).willReturn(Optional.of(company));

        // when
        UpdateCompanyResponse response = companyCommandService.updateCompany(comId, companyId, request);

        // then
        assertThat(response.getName()).isEqualTo("Updated Name");
        assertThat(company.getCeoName()).isEqualTo("New CEO");
    }

    @Test
    @DisplayName("회사 정보 수정 실패 - 권한 없음")
    void updateCompany_Forbidden() {
        // given
        Long comId = 1L;
        Long otherCompanyId = 2L;
        UpdateCompanyRequest request = UpdateCompanyRequest.builder().build();

        // when & then
        assertThatThrownBy(() -> companyCommandService.updateCompany(comId, otherCompanyId, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    @Test
    @DisplayName("회사 삭제 성공")
    void deleteCompany_Success() {
        // given
        Long comId = 1L;
        Long companyId = 1L;
        Company company = Company.builder()
                .companyId(comId)
                .isDeleted('N')
                .build();

        given(companyRepository.findById(comId)).willReturn(Optional.of(company));

        // when
        companyCommandService.deleteCompany(comId, companyId);

        // then
        assertThat(company.getIsDeleted()).isEqualTo('Y');
    }
}
