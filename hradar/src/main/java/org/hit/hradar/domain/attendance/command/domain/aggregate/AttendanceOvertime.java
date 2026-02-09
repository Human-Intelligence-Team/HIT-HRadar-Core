package org.hit.hradar.domain.attendance.command.domain.aggregate;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "attendance_overtime")
@Getter
@NoArgsConstructor
public class AttendanceOvertime extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "overtime_id")
    private Long overtimeId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "doc_id", nullable = false)
    private Long docId;

    @Column(name = "overtime_date", nullable = false)
    private LocalDate overtimeDate;

    @Column(name = "overtime_minutes", nullable = false)
    private Integer overtimeMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private AttendanceApprovalStatus status = AttendanceApprovalStatus.REQUESTED;

    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    public static AttendanceOvertime create(Long empId, Long docId, LocalDate overtimeDate, Integer overtimeMinutes) {
        AttendanceOvertime overtime = new AttendanceOvertime();
        overtime.empId = empId;
        overtime.docId = docId;
        overtime.overtimeDate = overtimeDate;
        overtime.overtimeMinutes = overtimeMinutes;
        overtime.status = AttendanceApprovalStatus.APPROVED; // 결재 승인 이벤트에서 처리하므로 바로 APPROVED
        return overtime;
    }

    public void approve() {
        this.status = AttendanceApprovalStatus.APPROVED;
    }

    public void reject() {
        this.status = AttendanceApprovalStatus.REJECTED;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
