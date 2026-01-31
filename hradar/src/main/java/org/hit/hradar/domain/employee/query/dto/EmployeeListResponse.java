package org.hit.hradar.domain.employee.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;

@Getter
public class EmployeeListResponse {

  private final List<EmployeeResponse> employees;

  private EmployeeListResponse(List<EmployeeResponse> employees) {
    this.employees = employees;
  }

  public static EmployeeListResponse of(List<EmployeeResponse> employees) {
    return new EmployeeListResponse(employees);
  }
}
