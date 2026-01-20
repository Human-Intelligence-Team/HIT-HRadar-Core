package org.hit.hradar.domain.employee.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.employee.query.dto.EmployeeForOrgChartResponse; // Import the DTO
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeQueryMapper {

    Optional<EmployeeResponse> findEmployeeById(Long empId, Long comId);

    List<EmployeeResponse> findAllEmployeesByCompany(Long comId);

    List<EmployeeForOrgChartResponse> findEmployeesForOrgChart(Long comId); // Add this method
}