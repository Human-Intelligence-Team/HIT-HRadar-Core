package org.hit.hradar.domain.employee.query.mapper;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.department.query.dto.EmployeeForOrgChartResponse; // Import the DTO
import org.hit.hradar.domain.employee.query.dto.EmployeeResponse;

import java.util.List;

@Mapper
public interface EmployeeQueryMapper {

    List<EmployeeForOrgChartResponse> findEmployeesForOrgChart(Long comId);

    Optional<EmployeeResponse> findById(@Param("comId") Long comId, @Param("empId") Long empId);

    List<EmployeeResponse> findList(
      @Param("comId") Long comId,
      @Param("deptId") Long deptId,
      @Param("positionId") Long positionId,
      @Param("employeeName") String employeeName
    );
}