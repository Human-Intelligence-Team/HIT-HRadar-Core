package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    private EvaluationAssignment(
            EvaluationType evaluationType,
            Long evaluatorId,
            Long evaluateeId
    ) {
        this.evaluationType = evaluationType;
        this.evaluatorId = evaluatorId;
        this.evaluateeId = evaluateeId;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }

    public boolean isDeleted() {
        return this.isDeleted == 'Y';
    }
}
