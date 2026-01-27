package org.hit.hradar.domain.salary.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;
import org.hit.hradar.domain.salary.command.domain.aggregate.BasicSalary;
import org.hit.hradar.domain.salary.command.domain.repository.BasicSalaryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicSalaryCommandService {

  private final BasicSalaryRepository basicSalaryRepository;

  /**
   * 기본급 등록
   * @param salaries
   */
  public void createBasicSalaryApproval(Long docId, List<SalaryDTO> salaries) {

    List<BasicSalary> basicSalaries =
        salaries.stream()
            .map(salary ->
                new BasicSalary(
                    docId
                    , salary.getEmpId()
                    , salary.getBasicSalary()
                    , salary.getIncreaseRate()
                    , salary.getIncreaseAmount()
                    , salary.getSalaryIncreaseType()
                    , salary.getRemark()
                )).toList();

//    basicSalaryRepository.saveAll(basicSalaries);

  }

  /**
   * 기본급 리스트
   * @param docId
   * @return
   */
  public List<BasicSalary> getBasicSalariesByDocId(Long docId) {
    return basicSalaryRepository.findAllByDocId(docId);
  }

  /**
   * 기본급 리스트 삭제
   * @param docId
   */
  public void deleteBasicSalariesByDocId(Long docId) {
    basicSalaryRepository.deleteAllByDocId(docId);
  }
}
