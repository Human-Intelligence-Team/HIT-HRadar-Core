package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyGradeCommandService {

    private final GradeRepository gradeRepository;

    public void registerCompanyGrade(Long compId, RegisterCompanyGradeRequestDto request){

        //회사 내 등급 코드 중복 체크
        if(gradeRepository.existsByCompanyIdAndGradeNameAndIsDeleted(
                compId,
                request.getGradeName(),
                'N'
        )){
            throw new BusinessException(GradingErrorCode.DUPLICATE_GRADE_NAME);
        }

        //회사 내 등급 순서 중복 체크
        if(gradeRepository.existsByCompanyIdAndGradeOrderAndIsDeleted(
                compId,
                request.getGradeOrder(),
                'N'
        )){
            throw new BusinessException(GradingErrorCode.DUPLICATE_GRADE_ORDER);
        }

        //Grade 생성
        Grade grade = Grade.builder()
                .companyId(compId)
                .gradeName(request.getGradeName())
                .gradeOrder(request.getGradeOrder())
                .build();

        gradeRepository.save(grade);
    }


}
