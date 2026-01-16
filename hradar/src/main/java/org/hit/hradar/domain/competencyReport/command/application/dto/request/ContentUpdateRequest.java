package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.ContentType;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Level;

@Getter
public class ContentUpdateRequest {

  private Long contentId;

  @NotBlank(message = "제목은 필수입니다.")
  private String title;

  @NotNull(message = "컨텐츠 타입은 필수입니다.")
  private ContentType type;

  @NotNull(message = "컨텐츠 타입은 필수입니다.")
  private Level level;

  private Integer learningTime;

  @Size(max = 1000, message = "리소스 경로는 1000자 이내로 입력해주세요.")
  private String resourcePath;

  @Size(max = 2000, message = "비고는 2000자 이내로 입력해주세요.")
  private String notes;

  private  Character isDeleted;

  private List<Long> tags; // 태그 id 리스트


}
