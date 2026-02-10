package org.hit.hradar.domain.employee.query.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.employee.query.dto.EmployeeMovementHistoryResponse;

@Mapper
public interface EmployeeMovementHistoryQueryMapper {
    List<EmployeeMovementHistoryResponse> findByCompanyAndEmpId(
            @Param("comId") Long comId,
            @Param("empId") Long empId);

    List<EmployeeMovementHistoryResponse> findAllByCompanyId(@Param("comId") Long comId);
}
