package org.hit.hradar.domain.competencyReport.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.hit.hradar.domain.competencyReport.query.dto.response.TagSearchResponse;
import org.hit.hradar.domain.competencyReport.query.dto.request.TagSearchRequest;
import org.hit.hradar.domain.competencyReport.query.mapper.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagQueryService {

  private final TagMapper tagMapper;

  /**
   * 태그 목록 조회
   * @param request
   * @return
   */
  public TagSearchResponse tags(TagSearchRequest request) {

     List<TagDTO> response = tagMapper.findAllTags(request);
     return TagSearchResponse.tags(response);

  }
}
