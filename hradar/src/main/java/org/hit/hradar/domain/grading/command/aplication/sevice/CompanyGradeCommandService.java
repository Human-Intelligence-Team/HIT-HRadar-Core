package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.UpdateCompanyGradeRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.port.EvaluationCycleStatusProvider;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyGradeCommandService {

    private final GradeRepository gradeRepository;
    private final EvaluationCycleStatusProvider evaluationCycleStatusProvider;


    public Long registerCompanyGrade(Long compId, RegisterCompanyGradeRequestDto request){

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

        return grade.getGradeId();
    }

    //등급 수정
    public void updateCompanyGrade(
            Long companyId,
            Long gradeId,
            UpdateCompanyGradeRequestDto request
    ){
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new BusinessException(GradingErrorCode.GRADE_NOT_FOUND));

        //회사 소속 체크
        if (!grade.getCompanyId().equals(companyId)) {
            throw new BusinessException(GradingErrorCode.GRADE_COMPANY_MISMATCH);
        }

        //평가 진행중이면 수정 불가
        validateNotInProgressEvaluation(companyId);

        //등급명 중복 체크
        if (!grade.getGradeName().equals(request.getGradeName())
                && gradeRepository.existsByCompanyIdAndGradeNameAndIsDeleted(
                companyId,
                request.getGradeName(),
                'N'
        )){
            throw new BusinessException(GradingErrorCode.DUPLICATE_GRADE_NAME);
        }

        //TODO: 나중에 필요하면 등급 순서 중복 체크

        grade.update(
                request.getGradeName(),
                request.getGradeOrder()
        );

    }

    //등급 삭제
    public void deleteCompanyGrade(
            Long companyId,
            Long gradeId
    ){
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new BusinessException(GradingErrorCode.GRADE_NOT_FOUND));

        if (!grade.getCompanyId().equals(companyId)) {
            throw new BusinessException(GradingErrorCode.GRADE_COMPANY_MISMATCH);
        }

        //  평가 진행 중이면 삭제 불가
        validateNotInProgressEvaluation(companyId);

        //  Soft Delete
        grade.delete();
    }

    private void validateNotInProgressEvaluation(Long companyId) {
        if (evaluationCycleStatusProvider.existsInProgressCycle(companyId)) {
            throw new BusinessException(
                    GradingErrorCode.GRADE_CANNOT_BE_MODIFIED
            );
        }
    }

}
