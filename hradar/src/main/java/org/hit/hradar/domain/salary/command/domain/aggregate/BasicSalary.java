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
@Table(name = "basic_salary_employee")
public class BasicSalary  extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "basic_salary_employee_id", nullable = false)
  private Long basicSalaryId;

  @Column(name = "doc_id", nullable = false)
  private Long docId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "basic_salary", nullable = false)
  private Long basicSalary;

  @Column(name = "increase_rate", precision = 5, scale = 2)
  private BigDecimal increaseRate;

  @Column(name = "increase_amount")
  private Integer increaseAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "salary_increase_type", nullable = false)
  private SalaryIncreaseType salaryIncreaseType;

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

  public BasicSalary(Long docId, Long empId, Long basicSalary, BigDecimal increaseRate
      , Integer increaseAmount, SalaryIncreaseType salaryIncreaseType, String remark) {
    this.docId = docId;
    this.empId = empId;
    this.basicSalary = basicSalary;
    this.increaseRate = increaseRate;
    this.increaseAmount = increaseAmount;
    this.salaryIncreaseType = salaryIncreaseType;
    this.remark = remark;
  }

}
