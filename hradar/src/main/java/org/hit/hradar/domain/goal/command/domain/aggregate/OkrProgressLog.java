package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "okr_progress_log")
@Getter
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

    //created_at, updated_at

    /*@Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;*/

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';
}
