package org.hit.hradar.domain.grading.command.domain.aggregate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    //회사 ID
    @Column(name = "com_id", nullable = false)
    private Long companyId;

    //등급 코드
    @Column(name = "grade_name", nullable = false, length = 10)
    private String gradeName;

    //등급 순서
    @Column(name = "grade_order", nullable = false)
    private Integer gradeOrder;

    //created_at, updated_at, created_by, updated_by

    //삭제여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    @Builder
    private Grade(
            Long companyId,
            String gradeName,
            Integer gradeOrder
    ){
        this.companyId = companyId;
        this.gradeName = gradeName;
        this.gradeOrder = gradeOrder;
    }

    public void update(
            String gradeName,
            Integer gradeOrder
    ){
        this.gradeName = gradeName;
        this.gradeOrder = gradeOrder;
    }

    public void delete() {
        this.isDeleted = 'Y';
    }
}
