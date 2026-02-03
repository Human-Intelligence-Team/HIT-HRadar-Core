package org.hit.hradar.domain.grading.query.service;

import org.hit.hradar.domain.grading.query.dto.response.CompanyGradeWithRuleListResponseDto;
import org.hit.hradar.domain.grading.query.mapper.CompanyGradeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyGradeQueryServiceTest {

    @InjectMocks
    private CompanyGradeQueryService service;

    @Mock
    private CompanyGradeMapper companyGradeQueryMapper;

    @Test
    @DisplayName("회사 등급 및 정책 조회 - 성공")
    void getCompanyGradeWithRuleList_success() {
        // given
        Long companyId = 1L;
        CompanyGradeWithRuleListResponseDto dto = new CompanyGradeWithRuleListResponseDto();
        
        when(companyGradeQueryMapper.findCompanyGradesWithRule(companyId)).thenReturn(List.of(dto));

        // when
        List<CompanyGradeWithRuleListResponseDto> result = service.getCompanyGradeWithRuleList(companyId);

        // then
        assertThat(result).hasSize(1);
        verify(companyGradeQueryMapper).findCompanyGradesWithRule(companyId);
    }
}
