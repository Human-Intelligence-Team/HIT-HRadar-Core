package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluation_question")
@Getter
public class EvaluationQuestion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    //섹션 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private EvaluationSection section;

    //문항 유형(객관식, 주관식, 점수형)
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    //문항 내용
    @Column(name = "question_content", nullable = false, length = 500)
    private String questionContent;

    //평점 척도(Rating일때만 사용)
    @Column(name = "rating_scale")
    private Integer ratingScale;

    //객관식 선택지
    @OneToMany(
            //ObjectiveOption 엔티티 안의 필드명
            mappedBy = "question",
            //부모(Question)에 대한 영속성 작업을 자식(Option)에게 전파해준다
            cascade = CascadeType.ALL,
            //부모 컬렉션에서 빠진 자식은 DB에서도 삭제
            orphanRemoval = true
    )
    private List<ObjectiveOption> options = new ArrayList<>();

    public void addOption(ObjectiveOption option) {
        options.add(option);
        option.setQuestion(this);
    }

    public void removeOption(ObjectiveOption option) {
        options.remove(option);
        option.setQuestion(null);
    }

    //필수 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "is_required", nullable = false)
    private RequiredStatus requiredStatus = RequiredStatus.OPTIONAL;

    //created_at, updated_at, created_by, updated_by
}
