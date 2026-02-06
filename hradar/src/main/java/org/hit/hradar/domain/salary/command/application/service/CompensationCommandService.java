package org.hit.hradar.domain.salary.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationSalary;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;
import org.hit.hradar.domain.salary.command.domain.repository.CompensationSalaryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompensationCommandService {

  private final CompensationSalaryRepository compensationSalaryRepository;

  /**
   * 변동보상 결재 등록
   * @param docId
   * @param compensations
   */
  public void createCompensationSalaryApproval(Long docId, List<SalaryDTO> compensations) {

    List<CompensationSalary> compensationSalaries =
        compensations.stream()
                .map(compensation -> new CompensationSalary(
                      compensation.getCompensationType()
                    , docId
                    , compensation.getEmpId()
                    , compensation.getAmount()
                    , compensation.getRate()
                    , compensation.getRemark()
                    )).toList();

     compensationSalaryRepository.saveAllWithPolicy(compensationSalaries);

  }

  /**
   * 변동보상 목록 조회
   * @param docId
   * @return
   */
  public List<CompensationSalary> getCompensationSalariesByDocId(Long docId) {
    return compensationSalaryRepository.findAllByDocId(docId);
  }

  /**
   * 변동보상 삭제
   * @param docId
   */
  public void deleteCompensationSalariesByDocId(Long docId) {
    compensationSalaryRepository.deleteAllByDocId(docId);
  }
}
