package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "kpi_progress_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KpiProgressLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kpi_log_id")
    private Long kpiLogId;

    //kpi와 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_id", nullable = false)
    private KpiDetail kpi;

    //log일자
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    //작성자
    @Column(name = "log_owner_id", nullable = false)
    private Long logOwnerId;

    //성과 값
    @Column(name = "log_value")
    private Integer logValue;

    // created_at, updated_at, created_by, updated_by

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    @Builder
    public KpiProgressLog (
            KpiDetail kpi,
            LocalDate logDate,
            Long logOwnerId,
            Integer logValue
    ){
        this.kpi = kpi;
        this.logDate = logDate;
        this.logOwnerId = logOwnerId;
        this.logValue = logValue;
    }
}
