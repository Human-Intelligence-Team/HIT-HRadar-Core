package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "okr_progress_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class OkrProgressLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "okr_log_id")
    private Long okrLogId;

    //kr 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_result_id", nullable = false)
    private OkrKeyResult keyResult;

    //진척도
    @Column(name = "current_progress", nullable = false)
    private Integer currentProgress;

    //log일자
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    //작성자
    @Column(name = "log_owner_id")
    private Long logOwnerId;

    //created_at, updated_at, created_by, updated_by

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    @Builder
    public OkrProgressLog(
            OkrKeyResult keyResult,
            Integer currentProgress,
            LocalDate logDate,
            Long logOwnerId
    ) {
        this.keyResult = keyResult;
        this.currentProgress = currentProgress;
        this.logDate = logDate;
        this.logOwnerId = logOwnerId;

    }
}
