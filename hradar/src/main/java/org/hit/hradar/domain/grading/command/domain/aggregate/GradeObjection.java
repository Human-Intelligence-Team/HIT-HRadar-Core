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
    private Long id;

    // 대상 개인 등급 ID
    @Column(name = "individual_grade_id", nullable = false)
    private Long individualGradeId;

    //이의 사유
    @Column(name = "objection_reason", nullable = false)
    private String reason;

    //상태
    @Enumerated(EnumType.STRING)
    @Column(name = "objection_status", nullable = false)
    private ObjectionStatus status = ObjectionStatus.REVIEWING;

    //결과
    @Column(name = "objection_result")
    private String result;

    // 처리자 ID
    @Column(name = "resolved_by")
    private Long resolvedByEmpId;

    //created_at, updated_at
}
