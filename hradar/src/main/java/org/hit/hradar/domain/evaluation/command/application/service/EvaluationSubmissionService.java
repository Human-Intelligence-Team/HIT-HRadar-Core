package org.hit.hradar.domain.evaluation.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.AssignmentStatus;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EvaluationSubmissionService {

    private final EvaluationAssignmentRepository evaluationAssignmentRepository;


    //응답 데이터는 이미 저장 되어있다고 가정
    @Transactional
    public void submit(Long assignmentId) {

        EvaluationAssignment assignment = evaluationAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("assignmentId 없음"));

        if (assignment.getStatus() == AssignmentStatus.SUBMITTED) {
            throw new IllegalStateException("이미 제출된 평가입니다.");
        }

        assignment.submit(LocalDateTime.now());
    }
}
