package org.hit.hradar.domain.employee.query.service.provider;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;

public interface EmployeeProviderService {

  Employee getEmployee(Long employeeId);

}
