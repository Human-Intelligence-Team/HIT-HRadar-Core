package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
        name = "evaluation_response",
        //같은 문항에 한번만 응답
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_assignment_question",
                        columnNames = {"assignment_id", "question_id"}
                )
        }
)
@Getter
public class EvaluationResponse extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    //평가 배정 id
    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    //문항 id
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    //객관식 선택지
    @Column(name = "option_id")
    private Long selectedOptionId;

    //점수형 응답
    @Column(name = "response_score")
    private Integer responseScore;

    //주관식 응답
    @Column(name = "response_text")
    private String responseText;
}
