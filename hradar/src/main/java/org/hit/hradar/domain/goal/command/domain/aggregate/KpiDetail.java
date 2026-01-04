package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "kpi_detail")
@Getter
public class KpiDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpi_id")
    private Long id;

    //목표
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    /*---------KPI 정보---------*/
    //지표명
    @Column(name = "kpi_metric_name", nullable = false, length = 50)
    private String kpiMetricName;

    //시작 값
    @Column(name = "kpi_start_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal kpiStartValue;

    //종료값
    @Column(name = "kpi_target_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal kpiTargetValue;

    //created_at, updated_at
}
