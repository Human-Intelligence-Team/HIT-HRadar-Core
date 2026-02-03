package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.AssignIndividualGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.IndividualGradeObjectionApproveRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.IndividualGradeUpdateRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.IndividualGrade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.IndividualGradeRepository;
import org.hit.hradar.domain.grading.port.EvaluationCycleStatusProvider;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class IndividualGradeCommandService {

    private final IndividualGradeRepository individualGradeRepository;
    private final GradeRepository gradeRepository;
    private final EvaluationCycleStatusProvider  evaluationCycleStatusProvider;

    //개인 등급 등록
    public void assignIndividualGrade(
            Long compId,
            AssignIndividualGradeRequestDto request
    ){
        //평가 진행 중 여부
        if (!evaluationCycleStatusProvider.existsInProgressCycle(
                compId
        )) {
            throw new BusinessException(
                    GradingErrorCode.CYCLE_NOT_IN_PROGRESS
            );
        }


        IndividualGrade individualGrade = IndividualGrade.builder()
                .cycleId(request.getCycleId())
                .empId(request.getEmpId())
                .gradeId(request.getGradeId())
                .gradeReason(request.getGradeReason())
                .build();

        individualGradeRepository.save(individualGrade);
    }

    //수정
    public void updateIndividualGrade(
            Long individualGradeId,
            IndividualGradeUpdateRequestDto request
    ) {
        IndividualGrade grade = getIndividualGrade(individualGradeId);
        grade.update(request.getGradeId(), request.getGradeReason());
    }

    //제출
    public void submitIndividualGrade(Long individualGradeId) {
        IndividualGrade grade = getIndividualGrade(individualGradeId);
        grade.submit();
    }

    //승인
    public void approveIndividualGrade(Long individualGradeId) {
        IndividualGrade grade = getIndividualGrade(individualGradeId);
        grade.approve();
    }

    //삭제
    public void deleteIndividualGrade(Long individualGradeId) {
        IndividualGrade grade = getIndividualGrade(individualGradeId);
        grade.delete();
    }

    private IndividualGrade getIndividualGrade(Long id) {
        return individualGradeRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException(
                                GradingErrorCode.INDIVIDUAL_GRADE_NOT_FOUND
                        )
                );
    }

    //이의제기 시 승인
    public void approveByObjection(
            Long individualGradeId,
            IndividualGradeObjectionApproveRequestDto request
    ) {
        IndividualGrade grade = getIndividualGrade(individualGradeId);
        // 1. 이의제기 승인으로 등급/사유 수정
        grade.reviseByObjection(
                request.getGradeId(),
                request.getGradeReason()
        );
    }
}
