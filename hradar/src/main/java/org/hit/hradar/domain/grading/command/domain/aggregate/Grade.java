package org.hit.hradar.domain.grading.command.domain.aggregate;
import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
        name = "grade",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_company_grade_code",
                        columnNames = {"com_id", "grade_code"}
                )
        }
)
@Getter
public class Grade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    //회사 ID
    @Column(name = "com_id", nullable = false)
    private Long companyId;

    //등급 코드
    @Column(name = "grade_code", nullable = false, length = 10)
    private String gradeCode;

    //등급 순서
    @Column(name = "grade_order", nullable = false)
    private Integer gradeOrder;

    //created_at, updated_at, created_by, updated_by

    //삭제여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';
}
