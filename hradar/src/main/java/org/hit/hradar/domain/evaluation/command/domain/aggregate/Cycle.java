package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;
import org.hit.hradar.domain.evaluation.EvaluationErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cycle")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cycle extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cycleId;

    //회차명
    @Column(name = "cycle_name", nullable = false, length=100)
    private String cycleName;

    // 년도
    @Column(name = "year", nullable = false, length=4)
    private String year;

    // 분기
    @Enumerated(EnumType.STRING)
    @Column(name = "quarter" , nullable = false)
    private Quarter quarter;

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

    @Column(name = "is_comp_report_generated", nullable = false, length = 1)
    private Character isReportGenerated = 'N';

    @Builder
    private Cycle(
            String cycleName,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long empId
    ){
        this.cycleName = cycleName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.empId = empId;
        this.status = CycleStatus.DRAFT;
        this.isDeleted = 'N';
        this.isReportGenerated = 'N';
    }

    public void updateCycle(String cycleName,LocalDateTime startDate, LocalDateTime endDate){
        this.cycleName = cycleName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void closeCycle() {
        this.status = CycleStatus.CLOSED;
    }

    public void deleteCycle() {
        this.isDeleted = 'Y';
    }

    public void approveCycle() {
        this.status = CycleStatus.APPROVED;
    }

    //도메인 확인용
    public boolean isClosed() {
        return this.status == CycleStatus.CLOSED;
    }

    public void open() {
        if (this.status != CycleStatus.APPROVED) {
            throw new BusinessException(EvaluationErrorCode.NOT_CONFIRMED);
        }
        this.status = CycleStatus.IN_PROGRESS;
    }

    public void close() {
        this.status = CycleStatus.CLOSED;
    }
}
