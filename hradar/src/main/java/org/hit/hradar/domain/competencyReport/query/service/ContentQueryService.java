package org.hit.hradar.domain.competencyReport.query.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.ContentSearchResponse;
import org.hit.hradar.domain.competencyReport.query.mapper.ContentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentQueryService {

  private final ContentMapper contentMapper;


  /**
   * 학습 컨텐츠 목록 조회 (검색/조회)
   * @param request
   * @return
   */
  public ContentSearchResponse contents(ContentSearchRequest request) {

    //
    List<ContentRowDTO> contents = contentMapper.findAllContents(request);

    List<ContentDTO> result = contents.stream()
        .collect(Collectors.groupingBy(ContentRowDTO::getContentId))
        .entrySet().stream()
        .map(entry -> {

          // tag List
          List<TagDTO> tags = entry.getValue().stream()
              .map(f -> new TagDTO(f.getTagId(), f.getTagName()))
              .collect(Collectors.toList());

          ContentRowDTO first = entry.getValue().get(0);
          ContentDTO dto = new ContentDTO(
                first.getContentId()
              , first.getTitle()
              , first.getType()
              , first.getLevel()
              , first.getLearningTime()
              , first.getResourcePath()
              , first.getIsDeleted()
              ,  tags
          );
          return dto;
        }).toList();

    return new ContentSearchResponse(result);

  }
}
