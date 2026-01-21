package org.hit.hradar.domain.salary.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column(name = "compensation_type", nullable = false, length = 50)
  private String compensationType;

  @Column(name = "doc_id", nullable = false)
  private Long docId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "amount", nullable = false)
  private Long amount;

  @Column(name = "rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal rate;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted;


}
