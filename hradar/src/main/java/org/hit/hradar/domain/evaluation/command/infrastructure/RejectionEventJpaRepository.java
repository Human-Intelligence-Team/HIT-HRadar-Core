package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.RejectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RejectionEventJpaRepository extends JpaRepository<RejectionEvent, Long> {

    /**
     * 특정 사원의 전체 반려 횟수
     */
    long countByEmpId(Long empId);

    /**
     * 특정 사원의 기간 내 반려 횟수
     */
    long countByEmpIdAndRejectedAtBetween(
            Long empId,
            LocalDateTime start,
            LocalDateTime end
    );
}
