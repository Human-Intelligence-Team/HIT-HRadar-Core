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

    // 대상 개인 등급
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_grade_id", nullable = false)
    private IndividualGrade individualGrade;

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

    //담당자
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by", nullable = false)
    private Employee resolvedBy;*/

    //created_at, updated_at
}
