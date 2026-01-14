package org.hit.hradar.domain.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplication;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeCommandService {

  private final EmployeeRepository employeeRepository;

  public Employee createAdminEmployee(Long comId, Long accId, CompanyApplication app) {
    return employeeRepository.save(
        Employee.builder()
            .comId(comId)
            .accId(accId)
            .comAdminName(app.getComAdminName())
            .comAdminEmail(app.getComAdminEmail())
            .build()
    );
  }


}
