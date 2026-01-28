package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "cycle_evaluation_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleEvaluationType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cycle_eval_type_id")
    private Long cycleEvalTypeId;

    // 회차 ID
    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;

    // 평가 타입 ID
    @Column(name = "eval_type_id", nullable = false)
    private Long evalTypeId;

    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    @Builder
    private CycleEvaluationType(Long cycleId, Long evalTypeId) {
        this.cycleId = cycleId;
        this.evalTypeId = evalTypeId;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}

