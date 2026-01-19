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

  @Column(name = "increase_rate", nullable = false, precision = 5, scale = 2)
  private BigDecimal increaseRate;

  @Column(name = "increase_amount", nullable = false)
  private Integer increaseAmount;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private String isDeleted;


}
