package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "kpi_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KpiDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpi_id")
    private Long kpiId;

    //목표
    @ManyToOne(fetch = FetchType.LAZY)
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

    //created_at, updated_at, created_by, updated_by

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    @Builder
    private KpiDetail(
            Goal goal,
            String kpiMetricName,
            BigDecimal kpiStartValue,
            BigDecimal kpiTargetValue
    ) {
        this.goal = goal;
        this.kpiMetricName = kpiMetricName;
        this.kpiStartValue = kpiStartValue;
        this.kpiTargetValue = kpiTargetValue;
        this.isDeleted = 'N';
    }

    public static KpiDetail create(
            Goal goal,
            String metricName,
            BigDecimal startValue,
            BigDecimal targetValue
    ) {
        return KpiDetail.builder()
                .goal(goal)
                .kpiMetricName(metricName)
                .kpiStartValue(startValue)
                .kpiTargetValue(targetValue)
                .build();
    }
}
