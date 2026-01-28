package org.hit.hradar.domain.positions.query.dto;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.positions.query.dto.PositionResponse;

@Getter
public class PositionListResponse {

  private final List<PositionResponse> positions;

  private PositionListResponse(List<PositionResponse> positions) {
    this.positions = positions;
  }

  public static PositionListResponse of(List<PositionResponse> positions) {
    return new PositionListResponse(positions);
  }
}
