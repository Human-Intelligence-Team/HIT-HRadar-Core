package org.hit.hradar.domain.employee.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;

@Getter
public class EmployeeListResponse {

  private final List<EmployeeResponse> employees;
  private final long totalCount;
  private final int totalPages;

  private EmployeeListResponse(List<EmployeeResponse> employees, long totalCount, int totalPages) {
    this.employees = employees;
    this.totalCount = totalCount;
    this.totalPages = totalPages;
  }

  public static EmployeeListResponse of(List<EmployeeResponse> employees, long totalCount, int size) {
    int totalPages = (int) Math.ceil((double) totalCount / size);
    return new EmployeeListResponse(employees, totalCount, totalPages);
  }
}
