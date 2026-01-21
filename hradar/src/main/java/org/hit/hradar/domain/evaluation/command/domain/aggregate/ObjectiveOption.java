package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;
@Entity
@Table(name = "objective_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObjectiveOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    //문항 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private EvaluationQuestion question;

    //선택지 문구
    @Column(name = "option_content", nullable = false, length = 200)
    private String optionContent;

    //점수
    @Column(name = "option_score")
    private Integer optionScore;

    //부모(EvaluationQuestion)를 통해서만 연결 강제
    //연관관계 설정
    void setQuestion(EvaluationQuestion question) {
        this.question = question;
    }

    @Builder
    public ObjectiveOption(Long id, EvaluationQuestion question, String optionContent, Integer optionScore) {
        this.id = id;
        this.question = question;
        this.optionContent = optionContent;
    }
}
