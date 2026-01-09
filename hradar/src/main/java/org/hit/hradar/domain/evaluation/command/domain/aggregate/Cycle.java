package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cycle")
@Getter
public class Cycle extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cycleId;

    //회차명
    @Column(name = "cycle_name", nullable = false, length=100)
    private String cycleName;

    // 시작일
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    // 종료일
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CycleStatus status = CycleStatus.DRAFT;

    // 담당 사원 ID
    @Column(name = "emp_id", nullable = false)
    private Long empId;

    //created_at, updated_at

    /*@Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;*/

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';
}
