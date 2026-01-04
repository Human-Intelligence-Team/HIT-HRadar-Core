package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "kpi_progress_log",
        //한 날짜는 하나의 로그만
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_kpi_log_date",
                        columnNames = {"kpi_id", "log_date"}
                )
        })
@Getter
public class KpiProgressLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpi_log_id")
    private Long id;

    //kpi와 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_id", nullable = false)
    private KpiDetail kpiDetail;

    //log일자
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    //성과 값
    @Column(name = "log_value")
    private Integer logValue;

    //작성자
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private Employee updatedBy;*/

    // created_at, updated_at
}
