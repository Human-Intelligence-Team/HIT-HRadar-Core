package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "evaluation_assignment",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_assignment_unique",
                        columnNames = {
                                "eval_type_id",
                                "evaluator_id",
                                "evaluatee_id"
                        }
                )
        }
)
@Getter
public class EvaluationAssignment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    // 평가 유형 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_type_id", nullable = false)
    private EvaluationType evaluationType;

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

    //created_at, updated_at, created_by, updated_by

    //삭제여부
    @Column(name= "is_deleted", nullable = false)
    private Character isDeleted = 'N';
}
