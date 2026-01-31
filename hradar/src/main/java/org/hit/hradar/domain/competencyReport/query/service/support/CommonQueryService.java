package org.hit.hradar.domain.competencyReport.query.service.support;

import java.util.List;
import java.util.stream.Collectors;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.springframework.stereotype.Service;

@Service
public class CommonQueryService {

  /**
   * 학습 컨텐츠 목록 공통 조회
   */
  public List<ContentDTO> getContents (List<ContentRowDTO> contents) {

    List<ContentDTO> result = contents.stream()
        .collect(Collectors.groupingBy(ContentRowDTO::getContentId))
        .entrySet().stream()
        .map(entry -> {

          // tag List
          List<TagDTO> tags = entry.getValue().stream()
              .map(f -> new TagDTO(f.getTagId(), f.getTagName(), null))
              .toList();


          ContentRowDTO first = entry.getValue().get(0);
          ContentDTO dto = new ContentDTO(
              first.getContentId()
              , first.getTitle()
              , first.getType()
              , first.getTypeCode()
              , first.getTypeName()
              , first.getLevel()
              , first.getLevelCode()
              , first.getLevelName()
              , first.getLearningTime()
              , first.getResourcePath()
              , first.getIsDeleted()
              ,  tags
          );
          return dto;
        }).toList();

    return result;
  }


}
