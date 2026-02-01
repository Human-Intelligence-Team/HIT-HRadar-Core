package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "rejection_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RejectionEvent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rejection_id")
    private Long rejectionId;

    /**
     * 반려당한 사원 ID
     */
    @Column(name = "emp_id", nullable = false)
    private Long empId;


    @Column(name = "rejected_at", nullable = false)
    private LocalDateTime rejectedAt;



    public static RejectionEvent create(Long empId) {
        RejectionEvent event = new RejectionEvent();
        event.empId = empId;
        event.rejectedAt = LocalDateTime.now();
        return event;
    }
}
