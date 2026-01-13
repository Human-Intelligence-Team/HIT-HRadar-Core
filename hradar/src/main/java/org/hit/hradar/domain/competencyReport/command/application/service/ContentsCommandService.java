package org.hit.hradar.domain.competencyReport.command.application.service;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentsRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Content;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentsRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.ContentsTagRepository;
import org.hit.hradar.domain.competencyReport.command.domain.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentsCommandService {

  private final ContentsRepository contentsRepository;
  private final TagRepository tagRepository;
  private final ContentsTagRepository contentsTagRepository;

  /**
   * 학습 컨텐츠 등록
   * @param request
   */
  @Transactional
  public void createContents(ContentsRequest request){

    // userId 확인

    // 학습 컨텐츠 등록
    Content content = Content.create(request);
    //contentsRepository.save(contents);

    // 학습 컨텐츠 ID로 학습 컨텐츠로 tag 연결
    Long contentId = content.getId();

  }
}
