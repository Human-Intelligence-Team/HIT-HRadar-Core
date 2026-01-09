package org.hit.hradar.domain.grading.command.domain.aggregate;
import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "grade_objection")
@Getter
public class GradeObjection extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "objection_id")
    private Long objectionId;

    // 대상 개인 등급 ID
    @Column(name = "individual_grade_id", nullable = false)
    private Long individualGradeId;

    //이의 사유
    @Column(name = "objection_reason", nullable = false)
    private String objectionReason;

    //상태
    @Enumerated(EnumType.STRING)
    @Column(name = "objection_status", nullable = false)
    private ObjectionStatus objectionStatus  = ObjectionStatus.REVIEWING;

    //결과
    @Column(name = "objection_result")
    private String objectionResult;

    // 처리자 ID
    @Column(name = "resolved_by")
    private Long resolvedBy;

    //created_at, updated_at,created_by, updated_by

    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';
}
