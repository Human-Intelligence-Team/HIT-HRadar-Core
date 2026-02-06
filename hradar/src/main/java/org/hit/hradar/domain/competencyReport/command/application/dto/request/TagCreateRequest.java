package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagCreateRequest {

  @NotBlank(message = "태그를 1자 이상 작성해주세요.")
  @Size(message = "태그는 1자 부터 45자 까지 입력이 가능합니다.", min = 1, max = 45)
  private String tagName;


  public TagCreateRequest(String tagName) {
    this.tagName = tagName;
  }


}
