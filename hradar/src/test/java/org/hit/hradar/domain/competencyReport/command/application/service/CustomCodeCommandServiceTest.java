package org.hit.hradar.domain.competencyReport.command.application.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeCreateRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.request.CustomCodeDeleteRequest;
import org.hit.hradar.domain.competencyReport.command.application.dto.response.CustomCodeExistResponse;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.CustomCode;
import org.hit.hradar.domain.competencyReport.command.domain.repository.CustomCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomCodeCommandServiceTest {

  @InjectMocks
  private CustomCodeCommandService customCodeCommandService;

  @Mock
  private CustomCodeRepository customCodeRepository;

  @Test
  @DisplayName("커스텀 코드 생성 성공: 기존 코드를 조회하여 새로운 코드를 생성 및 저장한다.")
  void createCustomCode_Success() {
    // given
    Long comId = 1L;
    Long originCodeId = 100L;
    CustomCodeCreateRequest request = new CustomCodeCreateRequest(
        originCodeId, "NEW_CODE", "새이름", "설명", 'N'
    );

    // 1. 기존 코드 조회 결과 Mocking (그룹코드와 그룹명을 가져오기 위함)
    CustomCode originCode = mock(CustomCode.class);
    given(originCode.getGroupCode()).willReturn("GRP001");
    given(originCode.getGroupName()).willReturn("공통그룹");

    given(customCodeRepository.findByCustomCodeId(originCodeId)).willReturn(originCode);

    // when
    customCodeCommandService.createCustomCode(request, comId);

    // then
    then(customCodeRepository).should(times(1)).findByCustomCodeId(originCodeId);
    then(customCodeRepository).should(times(1)).save(any(CustomCode.class));
  }

  @Test
  @DisplayName("커스텀 코드 중복 확인 성공: 존재 여부를 정확히 반환한다.")
  void existCustomCode_Success() {
    // given
    String customCode = "CODE_01";
    Long comId = 1L;
    given(customCodeRepository.existsByCustomCodeAndComId(customCode, comId)).willReturn(true);

    // when
    CustomCodeExistResponse response = customCodeCommandService.existCustomCode(customCode, comId);

    // then
    assertThat(response.getExist()).isEqualTo(true);
    then(customCodeRepository).should(times(1)).existsByCustomCodeAndComId(customCode, comId);
  }

  @Test
  @DisplayName("커스텀 코드 삭제 성공: ID 리스트가 비어있지 않으면 삭제 로직이 호출된다.")
  void deleteCustomCode_Success() {
    // given
    Long comId = 1L;
    List<Long> codeIds = List.of(10L, 20L);
    CustomCodeDeleteRequest request = new CustomCodeDeleteRequest(codeIds);

    // when
    customCodeCommandService.deleteCustomCode(request, comId);

    // then
    then(customCodeRepository).should(times(1)).deleteByComIdAndCustomCodeIdIn(comId, codeIds);
  }

}