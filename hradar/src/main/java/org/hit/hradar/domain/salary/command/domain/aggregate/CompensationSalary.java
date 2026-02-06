package org.hit.hradar.domain.salary.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "compensation_salary_employee")
public class CompensationSalary extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "compensation_salary_employee_id", nullable = false)
  private Long compensationSalaryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "compensation_type", nullable = false)
  private CompensationType compensationType;

  @Column(name = "doc_id", nullable = false)
  private Long docId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "amount", nullable = false)
  private Long amount;

  @Column(name = "rate", precision = 5, scale = 2)
  private BigDecimal rate;

  @Column(name = "remark", length = 100)
  private String remark;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @PrePersist
  public void prePersist() {
    if (this.isDeleted == null) {
      this.isDeleted = 'N';
    }
  }

  public CompensationSalary(CompensationType compensationType, Long docId, Long empId, Long amount, BigDecimal rate, String remark) {
    this.compensationType = compensationType;
    this.docId = docId;
    this.empId = empId;
    this.amount = amount;
    this.rate = rate;
    this.remark = remark;
  }

}
