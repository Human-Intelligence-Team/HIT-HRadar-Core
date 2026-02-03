package org.hit.hradar.domain.evaluation.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationType extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eval_type_id")
    private Long evalTypeId;

    //평가 유형
    @Column(name = "type_name", nullable = false)
    private String typeName;

    // 회사 ID
    @Column(name = "company_id", nullable = false)
    private Long companyId;

    //created_at, updated_at, created_by, updated_by

    @Column(name = "is_deleted",  nullable = false)
    private Character isDeleted = 'N';

    @Builder
    private EvaluationType(Long companyId, String typeName) {
        this.companyId = companyId;
        this.typeName = typeName;
    }

    public void updateName(String typeName) {
        this.typeName = typeName;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }

}
