package org.hit.hradar.domain.evaluation.command.domain.aggregate;
import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation_section")
@Getter
public class EvaluationSection extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    //평가 유형
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_type_id", nullable = false)
    private EvaluationType evaluationType;

    //섹션명
    @Column(name = "section_title", nullable = false, length = 200)
    private String sectionTitle;

    //섹션 설명
    @Column(name = "section_description")
    private String sectionDescription;

    //created_at, updated_at, created_by, updated_by
}
