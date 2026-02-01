package org.hit.hradar.domain.evaluation.command.domain.aggregate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluation_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    // 섹션 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private EvaluationSection section;

    // 문항 유형
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    // 문항 내용
    @Column(name = "question_content", nullable = false, length = 500)
    private String questionContent;

    // 평점 척도 (RATING 전용)
    @Column(name = "rating_scale")
    private Integer ratingScale;

    // 필수 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "required_status", nullable = false)
    private RequiredStatus requiredStatus = RequiredStatus.OPTIONAL;

    // 객관식 선택지
    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ObjectiveOption> options = new ArrayList<>();

    /* 도메인 행위*/

    public void addOption(ObjectiveOption option) {
        options.add(option);
        option.setQuestion(this);
    }

    public void clearOptions() {
        options.clear();
    }

    public void updateContent(String content) {
        this.questionContent = content;
    }

    public void updateRequiredStatus(RequiredStatus status) {
        this.requiredStatus = status;
    }

    public void updateRatingScale(Integer ratingScale) {
        this.ratingScale = ratingScale;
    }

    @Builder
    private EvaluationQuestion(
            EvaluationSection section,
            QuestionType questionType,
            String questionContent,
            Integer ratingScale,
            RequiredStatus requiredStatus
    ) {
        this.section = section;
        this.questionType = questionType;
        this.questionContent = questionContent;
        this.ratingScale = ratingScale;
        this.requiredStatus =
                (requiredStatus == null) ? RequiredStatus.OPTIONAL : requiredStatus;
    }
}

