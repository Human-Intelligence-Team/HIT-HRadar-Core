package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "kpi_progress_log")
@Getter
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

    // created_at, updated_at

    /*@Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;*/

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';
}
