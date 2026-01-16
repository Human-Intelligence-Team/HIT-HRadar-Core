package org.hit.hradar.domain.employee.query.service.provider;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeProviderServiceImpl implements EmployeeProviderService {

  private final EmployeeRepository employeeRepository;

  /**
   * 사원의 정보 가져오기
   * @param employeeId
   * @return
   */
  @Override
  public Employee getEmployee(Long employeeId) {
    return employeeRepository.findById(employeeId).orElse(null);
  }
}
