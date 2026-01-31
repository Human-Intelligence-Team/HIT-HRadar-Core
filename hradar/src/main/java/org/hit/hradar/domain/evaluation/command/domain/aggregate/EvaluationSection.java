package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "evaluation_section")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationSection extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_eval_type_id", nullable = false)
    private CycleEvaluationType cycleEvaluationType;

    // 섹션명
    @Column(name = "section_title", nullable = false, length = 200)
    private String sectionTitle;

    // 섹션 설명
    @Column(name = "section_description")
    private String sectionDescription;

    // 섹션 순서
    @Column(name = "section_order", nullable = false)
    private Integer sectionOrder;

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    @Builder
    private EvaluationSection(
            CycleEvaluationType cycleEvaluationType,
            String sectionTitle,
            String sectionDescription,
            Integer sectionOrder
    ) {
        this.cycleEvaluationType = cycleEvaluationType;
        this.sectionTitle = sectionTitle;
        this.sectionDescription = sectionDescription;
        this.sectionOrder = sectionOrder;
    }

    public void update(String title, String description, Integer order) {
        this.sectionTitle = title;
        this.sectionDescription = description;
        this.sectionOrder = order;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
