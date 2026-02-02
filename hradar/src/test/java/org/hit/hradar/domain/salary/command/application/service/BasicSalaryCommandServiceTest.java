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
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.hit.hradar.domain.salary.command.domain.aggregate.SalaryIncreaseType;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BasicSalaryCommandServiceTest {
  @InjectMocks
  private BasicSalaryCommandService basicSalaryCommandService;

  @Mock
  private BasicSalaryRepository basicSalaryRepository;

  @Test
  @DisplayName("기본급 등록 성공: DTO 리스트가 서비스 로직을 통해 엔티티로 변환되어 저장된다.")
  void createBasicSalaryApproval_Success() {
    // 1. Given: SalaryDTO의 전체 필드 생성자 순서에 맞춰 데이터 준비
    Long docId = 100L;

    // 필드 순서: empId, remark, basicSalaryId, salaryIncreaseType, basicSalary, increaseRate, increaseAmount, ...
    // (앞서 정의한 11개 필드 생성자 기준, 필요한 필드 위주로 채움)
    SalaryDTO salary1 = new SalaryDTO(
        1L,                     // empId
        "비고1",                 // remark
        10L,                    // basicSalaryId
        SalaryIncreaseType.REGULAR, // salaryIncreaseType
        3000000L,               // basicSalary
        new BigDecimal("5.0"),  // increaseRate
        150000,                 // increaseAmount
        null, null, null, null  // 변동보상 필드 (기본급 등록 시에는 null 가능)
    );

    SalaryDTO salary2 = new SalaryDTO(
        2L,
        "비고2",
        20L,
        SalaryIncreaseType.PROMOTION,
        4000000L,
        new BigDecimal("3.0"),
        120000,
        null, null, null, null
    );

    List<SalaryDTO> salaries = List.of(salary1, salary2);

    // 2. When
    basicSalaryCommandService.createBasicSalaryApproval(docId, salaries);

    // 3. Then
    // 서비스 로직에서 saveAll(basicSalaries) 주석을 해제해야 테스트가 통과됩니다.
    then(basicSalaryRepository).should(times(1)).saveAll(anyList());
  }

  @Test
  @DisplayName("기본급 조회 성공: 문서 ID에 해당하는 기본급 리스트를 반환한다.")
  void getBasicSalariesByDocId_Success() {
    // given
    Long docId = 100L;
    List<BasicSalary> mockList = List.of(mock(BasicSalary.class), mock(BasicSalary.class));
    given(basicSalaryRepository.findAllByDocId(docId)).willReturn(mockList);

    // when
    List<BasicSalary> result = basicSalaryCommandService.getBasicSalariesByDocId(docId);

    // then
    assertThat(result).hasSize(2);
    then(basicSalaryRepository).should(times(1)).findAllByDocId(docId);
  }

  @Test
  @DisplayName("기본급 삭제 성공: 문서 ID를 기준으로 삭제 메서드가 호출된다.")
  void deleteBasicSalariesByDocId_Success() {
    // given
    Long docId = 100L;

    // when
    basicSalaryCommandService.deleteBasicSalariesByDocId(docId);

    // then
    then(basicSalaryRepository).should(times(1)).deleteAllByDocId(docId);
  }
}