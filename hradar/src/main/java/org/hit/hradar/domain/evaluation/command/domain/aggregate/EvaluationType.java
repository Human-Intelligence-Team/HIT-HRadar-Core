package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation_type")
@Getter
public class EvaluationType extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eval_type_id")
    private Long id;

    //평가 회차 연결
    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;

    //평가 유형
    @Enumerated(EnumType.STRING)
    @Column(name = "eval_type", nullable = false)
    private EvaluationTypeCode evalType;

    //created_at, updated_at

}
