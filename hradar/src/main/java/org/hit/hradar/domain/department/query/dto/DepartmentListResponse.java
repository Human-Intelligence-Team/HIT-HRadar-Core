package org.hit.hradar.domain.department.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.department.query.dto.DepartmentResponse;

@Getter
public class DepartmentListResponse {

  private final List<DepartmentResponse> departments;

  private DepartmentListResponse(List<DepartmentResponse> departments) {
    this.departments = departments;
  }

  public static DepartmentListResponse of(List<DepartmentResponse> departments) {
    return new DepartmentListResponse(departments);
  }
}
