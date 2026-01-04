package org.hit.hradar.domain.grading.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "grade_cycle")
@Getter
public class GradeCycle extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_cycle_id")
    private Long id;

    //회차명
    @Column(name = "grade_cycle_name", nullable = false, length = 100)
    private String cycleName;

    //회차 시작일
    @Column(name = "period_start_date", nullable = false)
    private LocalDate periodStartDate;

    //회차 종료일
    @Column(name = "period_end_date", nullable = false)
    private LocalDate periodEndDate;

    //회차 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_cycle_status", nullable = false)
    private GradeCycleStatus status = GradeCycleStatus.REVIEW;

    //created_at updated_at
}

