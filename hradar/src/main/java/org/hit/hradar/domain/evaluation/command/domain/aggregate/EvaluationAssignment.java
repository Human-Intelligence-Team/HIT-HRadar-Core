package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "evaluation_assignment",
        //같은 회차에 같은 유형으로 같은 사람이 같은 사람을 중복 평가 막음
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

    //평가 회차
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_id", nullable = false)
    private EvaluationCycle cycle;

    //평가 유형
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_type_id", nullable = false)
    private EvaluationType evaluationType;

    //평가자
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id", nullable = false)
    private Employee evaluator;*/

    //피평가자
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluatee_id", nullable = false)
    private Employee evaluatee;*/

    //제출 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false)
    private AssignmentStatus status = AssignmentStatus.PENDING;

    //제출 시각
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}