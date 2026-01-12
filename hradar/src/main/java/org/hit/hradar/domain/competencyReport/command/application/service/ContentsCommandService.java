package org.hit.hradar.domain.competencyReport.command.application.service;


import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.ContentsRequest;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Contents;
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
    Contents contents = Contents.create(request);
    //contentsRepository.save(contents);

    // 학습 컨텐츠 ID로 학습 컨텐츠로 tag 연결
    Long contentId = contents.getId();
    Long[] tagArr = request.getTagArr();

    // tagArr가 있을 경우에만 넣어주기
    if (tagArr != null && tagArr.length > 0) {
      // TODO : tagId가 없을 경우 어떻게 해야할지??
      Arrays.stream(tagArr).forEach(tagId -> {
        // tag 테이블에 tagId가 있는지 확인

      });
    }
  }
}
