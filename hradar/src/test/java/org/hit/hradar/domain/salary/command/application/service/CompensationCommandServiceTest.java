package org.hit.hradar.domain.salary.command.application.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.List;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationSalary;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;
import org.hit.hradar.domain.salary.command.domain.repository.CompensationSalaryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompensationCommandServiceTest {
  @InjectMocks
  private CompensationCommandService compensationCommandService;

  @Mock
  private CompensationSalaryRepository compensationSalaryRepository;

  @Test
  @DisplayName("변동보상 등록 성공: DTO 리스트가 엔티티로 변환되어 저장된다.")
  void createCompensationSalaryApproval_Success() {
    // given
    Long docId = 200L;

    // 이전에 정의한 11개 필드 생성자 기준 (필요한 변동보상 필드 위주로 채움)
    SalaryDTO salary1 = new SalaryDTO(
        1L, "비고1", null, null, null, null, null,
        300L, CompensationType.BONUS, 5000000L, new BigDecimal("10.0")
    );
    List<SalaryDTO> salaries = List.of(salary1);

    // when
    compensationCommandService.createCompensationSalaryApproval(docId, salaries);

    // then
    then(compensationSalaryRepository).should(times(1)).saveAll(anyList());
  }

  @Test
  @DisplayName("변동보상 목록 조회 성공: 문서 ID로 조회된 리스트를 반환한다.")
  void getCompensationSalariesByDocId_Success() {
    // given
    Long docId = 200L;
    List<CompensationSalary> mockList = List.of(mock(CompensationSalary.class));
    given(compensationSalaryRepository.findAllByDocId(docId)).willReturn(mockList);

    // when
    List<CompensationSalary> result = compensationCommandService.getCompensationSalariesByDocId(docId);

    // then
    assertThat(result).hasSize(1);
    then(compensationSalaryRepository).should(times(1)).findAllByDocId(docId);
  }

  @Test
  @DisplayName("변동보상 삭제 성공: 문서 ID를 기준으로 삭제 로직이 호출된다.")
  void deleteCompensationSalariesByDocId_Success() {
    // given
    Long docId = 200L;

    // when
    compensationCommandService.deleteCompensationSalariesByDocId(docId);

    // then
    then(compensationSalaryRepository).should(times(1)).deleteAllByDocId(docId);
  }
}