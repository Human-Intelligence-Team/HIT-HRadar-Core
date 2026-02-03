package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation_assignment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationAssignment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_eval_type_id", nullable = false)
    private CycleEvaluationType cycleEvaluationType;

    @Column(name = "evaluator_id", nullable = false)
    private Long evaluatorId;

    @Column(name = "evaluatee_id", nullable = false)
    private Long evaluateeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false)
    private AssignmentStatus status = AssignmentStatus.PENDING;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name= "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    @Builder
    private EvaluationAssignment(
            CycleEvaluationType cycleEvaluationType,
            Long evaluatorId,
            Long evaluateeId
    ) {
        this.cycleEvaluationType = cycleEvaluationType;
        this.evaluatorId = evaluatorId;
        this.evaluateeId = evaluateeId;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }

    public boolean isDeleted() {
        return this.isDeleted == 'Y';
    }

    public void submit(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
        this.status = AssignmentStatus.SUBMITTED;
    }

}
