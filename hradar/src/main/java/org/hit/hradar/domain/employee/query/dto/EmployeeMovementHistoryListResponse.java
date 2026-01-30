package org.hit.hradar.domain.employee.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryResponse;

@Getter
public class EmployeeMovementHistoryListResponse {

  private final List<EmployeeMovementHistoryResponse> histories;

  private EmployeeMovementHistoryListResponse(List<EmployeeMovementHistoryResponse> histories) {
    this.histories = histories;
  }

  public static EmployeeMovementHistoryListResponse of(
      List<EmployeeMovementHistoryResponse> histories
  ) {
    return new EmployeeMovementHistoryListResponse(histories);
  }
}
