package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.query.dto.CompetencyReportDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;

@Getter
public class CompetencyReportDetailResponse {

  // 회차, 사원정보, 역량 강화 리포트
  private CompetencyReportDTO  competencyReportDTO;

  // 학습 컨텐츠, 태그 리스트
  private List<ContentDTO> contents;

  public CompetencyReportDetailResponse(CompetencyReportDTO competencyReportDTO, List<ContentDTO> contents) {
    this.competencyReportDTO = competencyReportDTO;
    this.contents = contents;
  }

}
