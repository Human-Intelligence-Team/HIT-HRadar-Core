package org.hit.hradar.domain.competencyReport.query.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentType;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Level;

@Getter
@Setter
public class ContentSearchRequest {

  private String title; // 콘텐츠명
  private ContentType type;// 유형
  private Level level; // 난이도
  private Integer learningTime; // 학습시간
  private List<Long> tags; // 태그
  private Character isDeleted; // 사용여부

}
