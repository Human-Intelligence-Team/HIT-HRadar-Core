package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "evaluation_assignment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_assignment_unique",
                        columnNames = {
                                "cycle_id",
                                "eval_type_id",
                                "evaluator_id",
                                "evaluatee_id"
                        }
                )
        }
)
@Getter
public class EvaluationAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    // 평가 회차 ID
    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;

    // 평가 유형 ID
    @Column(name = "eval_type_id", nullable = false)
    private Long evaluationTypeId;

    // 평가자 ID
    @Column(name = "evaluator_id", nullable = false)
    private Long evaluatorId;

    // 피평가자 ID
    @Column(name = "evaluatee_id", nullable = false)
    private Long evaluateeId;

    // 제출 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false)
    private AssignmentStatus status = AssignmentStatus.PENDING;

    // 제출 시각
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
