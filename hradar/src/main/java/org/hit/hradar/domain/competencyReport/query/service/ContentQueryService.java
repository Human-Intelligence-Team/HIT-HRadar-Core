package org.hit.hradar.domain.competencyReport.query.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentTagRepository;
import org.hit.hradar.domain.competencyReport.competencyReportErrorCode.CompetencyReportErrorCode;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;
import org.hit.hradar.domain.competencyReport.query.dto.ContentRowDTO;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;
import org.hit.hradar.domain.competencyReport.query.dto.request.ContentSearchRequest;
import org.hit.hradar.domain.competencyReport.query.dto.response.ContentDetailResponse;
import org.hit.hradar.domain.competencyReport.query.dto.response.ContentSearchResponse;
import org.hit.hradar.domain.competencyReport.query.mapper.ContentMapper;
import org.hit.hradar.domain.competencyReport.query.mapper.TagMapper;
import org.hit.hradar.domain.competencyReport.query.service.support.CommonQueryService;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentQueryService {

  private final ContentMapper contentMapper;
  private final TagMapper tagMapper;
  private final CommonQueryService commonQueryService;

  /**
   * 학습 컨텐츠 목록 조회 (검색/조회)
   * @param request
   * @return
   */
  public ContentSearchResponse contents(ContentSearchRequest request, Long comId) {

    request.setComId(comId);
    List<ContentRowDTO> contents = contentMapper.findAllContents(request);

    // 학습 컨텐츠 목록 변환
    List<ContentDTO> result = commonQueryService.getContents(contents);
    return new ContentSearchResponse(result);

  }

  /**
   * 학습 컨텐츠 상세
   * @param id
   * @return
   */
  public ContentDetailResponse contentDetail(Long id, Long comId) {

    // content detail
    ContentDTO content = contentMapper.findContentByContentId(id, comId);
    if (content == null) {
      throw new BusinessException(CompetencyReportErrorCode.CONTENT_NOT_FOUND);
    }

    // content tag List
    List<TagDTO> tags = tagMapper.findAllTagsByContentId(id, comId);
    content.addTags(tags);

    return new ContentDetailResponse(content);
  }
}
