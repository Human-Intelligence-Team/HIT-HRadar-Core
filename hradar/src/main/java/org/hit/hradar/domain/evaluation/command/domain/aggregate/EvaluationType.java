package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation_type")
@Getter
@NoArgsConstructor
public class EvaluationType extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eval_type_id")
    private Long evalTypeId;

    //평가 회차 연결
    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;

    //평가 유형
    @Column(name = "eval_type_code", nullable = false)
    private String evalTypeCode;

    //created_at, updated_at, created_by, updated_by

    @Column(name = "is_deleted",  nullable = false)
    private Character isDeleted = 'N';

    @Builder
    EvaluationType(
            Long cycleId,
            String evalTypeCode
    ){
        this.cycleId = cycleId;
        this.evalTypeCode = evalTypeCode;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
