package org.hit.hradar.domain.companyApplication.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.companyApplication.query.dto.ComAppListItemResponse;

@Getter
public class ComAppSearchResponse {

  private final List<ComAppListItemResponse> applications;

  private ComAppSearchResponse(List<ComAppListItemResponse> applications) {
    this.applications = applications;
  }

  public static ComAppSearchResponse of(List<ComAppListItemResponse> applications) {
    return new ComAppSearchResponse(applications);
  }
}
