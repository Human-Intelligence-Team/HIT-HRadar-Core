package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "okr_key_result")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OkrKeyResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_result_id")
    private Long keyResultId;

    //목표 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goal goal;

    //kr내용
    @Column(name = "key_result_content", nullable = false, length = 300)
    private String content;

    //지표명
    @Column(name = "okr_metric_name", nullable = false, length = 50)
    private String okrMetricName;

    //목표 수치
    @Column(name = "key_target_value", nullable = false)
    private Integer targetValue;

    //달성 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "is_achieved", nullable = false)
    private AchieveStatus isAchieved = AchieveStatus.N;

    //created_at, updated_at, created_by, updated_by

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    @Builder
    private OkrKeyResult(
            Goal goal,
            String content,
            String okrMetricName,
            Integer targetValue
    ) {
        this.goal = goal;
        this.content = content;
        this.okrMetricName = okrMetricName;
        this.targetValue = targetValue;
    }

    public static OkrKeyResult create(
            Goal goal,
            String content,
            String metricName,
            Integer targetValue
    ) {
        return OkrKeyResult.builder()
                .goal(goal)
                .content(content)
                .okrMetricName(metricName)
                .targetValue(targetValue)
                .build();
    }

    public void update(
            String content,
            String metricName,
            Integer targetValue
    ) {
        this.content = content;
        this.okrMetricName = metricName;
        this.targetValue = targetValue;
    }

}
