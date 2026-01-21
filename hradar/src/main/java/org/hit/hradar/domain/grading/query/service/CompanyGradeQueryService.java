package org.hit.hradar.domain.grading.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.CompanyGradeWithRuleListResponseDto;
import org.hit.hradar.domain.grading.query.mapper.CompanyGradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyGradeQueryService {
    private final CompanyGradeMapper companyGradeQueryMapper;

    public List<CompanyGradeWithRuleListResponseDto> getCompanyGradeWithRuleList(
            Long companyId
    ) {
        return companyGradeQueryMapper.findCompanyGradesWithRule(companyId);
    }
}
