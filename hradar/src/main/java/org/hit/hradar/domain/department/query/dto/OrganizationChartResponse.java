package org.hit.hradar.domain.department.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.department.query.dto.DepartmentNode;

@Getter
public class OrganizationChartResponse {

  private final List<DepartmentNode> organization;

  private OrganizationChartResponse(List<DepartmentNode> organization) {
    this.organization = organization;
  }

  public static OrganizationChartResponse of(List<DepartmentNode> organization) {
    return new OrganizationChartResponse(organization);
  }
}
