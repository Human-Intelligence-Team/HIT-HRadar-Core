package org.hit.hradar.domain.grading.command.aplication.sevice;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.domain.grading.command.aplication.dto.request.AssignDeptGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.DeptGradeUpdateRequestDto;
import org.hit.hradar.domain.grading.command.domain.aggregate.DeptGrade;
import org.hit.hradar.domain.grading.command.domain.repository.DeptGradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.port.EvaluationCycleStatusProvider;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeptGradeCommandService {

    private final DeptGradeRepository deptGradeRepository;
    private final GradeRepository gradeRepository;
    private final EvaluationCycleStatusProvider evaluationCycleStatusProvider;

    public void assignDeptGrade(
            AssignDeptGradeRequestDto request,
            Long employeeId
    ){
        //평가 회차 진행중 여부
        if (!evaluationCycleStatusProvider.existsInProgressCycle(
                request.getCompanyId()
        )) {
            throw new BusinessException(
                    GradingErrorCode.CYCLE_NOT_IN_PROGRESS
            );
        }

        DeptGrade deptGrade = DeptGrade.builder()
                .cycleId(request.getCycleId())
                .departmentId(request.getDepartmentId())
                .gradeId(request.getGradeId())
                .gradeReason(request.getGradeReason())
                .assignerId(employeeId).build();

        deptGradeRepository.save(deptGrade);

    }

    //등급 수정
    public void updateDeptGrade(
            Long deptGradeId,
            DeptGradeUpdateRequestDto request
    ) {
        DeptGrade deptGrade = getDeptGrade(deptGradeId);
        deptGrade.update(request.getGradeId(), request.getGradeReason());
    }

    //등급 제출
    public void submitDeptGrade(Long deptGradeId) {
        DeptGrade deptGrade = getDeptGrade(deptGradeId);

        if (deptGrade.getIsDeleted() == 'Y') {
            throw new BusinessException(GradingErrorCode.GRADE_NOT_FOUND);
        }
        deptGrade.submit();
    }

    //등급 승인
    public void approveDeptGrade(
            Long deptGradeId,
            Long approverId
    ) {
        DeptGrade deptGrade = getDeptGrade(deptGradeId);
        deptGrade.approve(approverId);
    }

    //팀등급 삭제
    public void deleteDeptGrade(Long deptGradeId) {
        DeptGrade deptGrade = getDeptGrade(deptGradeId);
        deptGrade.delete();
    }

    private DeptGrade getDeptGrade(Long deptGradeId) {
        return deptGradeRepository.findByDeptGradeId(deptGradeId)
                .orElseThrow(() ->
                        new BusinessException(
                                GradingErrorCode.DEPT_GRADE_NOT_FOUND
                        )
                );
    }
}
