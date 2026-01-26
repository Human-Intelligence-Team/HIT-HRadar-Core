package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;

@Getter
public class CompensationSearchRequest {

  private CompensationType compensationType;
  private String startDate;
  private String endDate;

  public CompensationSearchRequest(CompensationType compensationType,  String startDate, String endDate) {
    this.compensationType = compensationType;
    this.startDate = startDate;
    this.endDate = endDate;
  }

}
