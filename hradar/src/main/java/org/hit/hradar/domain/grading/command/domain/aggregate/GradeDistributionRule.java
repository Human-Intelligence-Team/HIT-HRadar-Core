package org.hit.hradar.domain.grading.command.domain.aggregate;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
        name = "grade_distribution_rule",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_grade_ratio",
                        columnNames = {"grade_id"}
                )
        }
)
@Getter
public class GradeDistributionRule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_rule_id")
    private Long gradeRuleId;

    //등급ID - grade가 변경되어도 규칙은 남아있어야함으로 fk 연결 끊음
    @Column(name = "grade_id", nullable = false)
    private Long gradeId;

    //최소비율
    @Column(name = "min_ratio", nullable = false)
    private Integer minRatio;

    //최대비율
    @Column(name = "max_ratio", nullable = false)
    private Integer maxRatio;

    //created_at, updated_at, created_by, updated_by

    //소프트 삭제 여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    @Builder
    private GradeDistributionRule(
            Long gradeId,
            Integer minRatio,
            Integer maxRatio
    ){
        this.gradeId = gradeId;
        this.minRatio = minRatio;
        this.maxRatio = maxRatio;
    }

    public void update(Integer minRatio, Integer maxRatio) {
        this.minRatio = minRatio;
        this.maxRatio = maxRatio;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
