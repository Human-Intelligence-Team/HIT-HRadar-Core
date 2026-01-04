package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "okr_key_result")
@Getter
public class OkrKeyResult extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_result_id")
    private Long id;

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

    //created_at, updated_at
}
