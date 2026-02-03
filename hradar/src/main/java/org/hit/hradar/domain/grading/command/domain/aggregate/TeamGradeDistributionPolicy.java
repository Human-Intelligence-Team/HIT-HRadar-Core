package org.hit.hradar.domain.grading.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
        name = "team_grade_distribution_policy",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_team_member_grade",
                        columnNames = {"company_id", "team_grade_id", "member_grade_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamGradeDistributionPolicy extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    // 팀이 받은 등급
    @Column(name = "team_grade_id", nullable = false)
    private Long teamGradeId;

    // 팀원에게 부여할 등급
    @Column(name = "member_grade_id", nullable = false)
    private Long memberGradeId;

    @Column(nullable = false)
    private Integer minRatio;

    @Column(nullable = false)
    private Integer maxRatio;

    @Column(nullable = false)
    private Character isDeleted = 'N';

    @Builder
    private TeamGradeDistributionPolicy(
            Long companyId,
            Long teamGradeId,
            Long memberGradeId,
            Integer minRatio,
            Integer maxRatio
    ) {
        this.companyId = companyId;
        this.teamGradeId = teamGradeId;
        this.memberGradeId = memberGradeId;
        this.minRatio = minRatio;
        this.maxRatio = maxRatio;
    }

    public void updateRatio(Integer minRatio, Integer maxRatio) {
        this.minRatio = minRatio;
        this.maxRatio = maxRatio;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
