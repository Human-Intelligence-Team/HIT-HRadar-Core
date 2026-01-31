package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "evaluation_response")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationResponse extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long responseId;

    // 어떤 평가 배정에 대한 응답인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private EvaluationAssignment assignment;

    //어떤 문항인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private EvaluationQuestion question;

    //응답 유형
    @Enumerated(EnumType.STRING)
    @Column(name = "response_type", nullable = false)
    private QuestionType responseType;

    // 평점형
    @Column(name = "score")
    private Integer score;

    // 주관식
    @Column(name = "text_answer", length = 2000)
    private String textAnswer;

    // 객관식
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ObjectiveOption selectedOption;


    @Builder
    private EvaluationResponse(
            EvaluationAssignment assignment,
            EvaluationQuestion question,
            QuestionType responseType,
            Integer score,
            String textAnswer,
            ObjectiveOption selectedOption
    ) {
        this.assignment = assignment;
        this.question = question;
        this.responseType = responseType;
        this.score = score;
        this.textAnswer = textAnswer;
        this.selectedOption = selectedOption;
    }

    public void updateAnswer(
            QuestionType responseType,
            Integer score,
            String textAnswer,
            ObjectiveOption selectedOption
    ) {
        this.responseType = responseType;
        this.score = score;
        this.textAnswer = textAnswer;
        this.selectedOption = selectedOption;
    }
}
