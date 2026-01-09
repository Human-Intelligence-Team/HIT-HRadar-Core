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

    //created_at, updated_at

    /*@Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;*/

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    /*@OneToMany(
            mappedBy = "kpi",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<KpiProgressLog> progressLogs = new ArrayList<>();*/

}
