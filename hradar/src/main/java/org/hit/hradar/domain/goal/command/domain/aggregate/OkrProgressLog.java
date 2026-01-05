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
    private Long id;

    //kr 연결
    @Column(name = "key_result_id", nullable = false)
    private Long keyResultId;

    //진척도
    @Column(name = "current_progress", nullable = false)
    private Integer currentProgress;

    //log일자
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    //작성자
    @Column(name = "updated_by")
    private Long updatedByEmpId;

    //created_at, updated_at
}
