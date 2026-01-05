package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation_cycle")
@Getter
public class EvaluationCycle extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cycle_id")
    private Long id;

    //회차명
    @Column(name = "cycle_name", nullable = false, length = 100)
    private String cycleName;

    //회차 시작일
    @Column(name = "cycle_start_date", nullable = false)
    private LocalDate cycleStartDate;

    //회차 종료일
    @Column(name = "cycle_end_date", nullable = false)
    private LocalDate cycleEndDate;

    //회차 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "cycle_status", nullable = false)
    private EvaluationCycleStatus cycleStatus = EvaluationCycleStatus.DRAFT;

    //회차 생성자
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    //created_at, updated_at
}
